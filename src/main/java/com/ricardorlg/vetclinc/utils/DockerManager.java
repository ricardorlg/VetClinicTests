package com.ricardorlg.vetclinc.utils;

import org.slf4j.Logger;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.containers.wait.strategy.Wait;

import java.time.Duration;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

import static org.slf4j.LoggerFactory.getLogger;

public final class DockerManager {
    public static final Logger LOGGER = getLogger(DockerManager.class);
    private static final GenericContainer<?> container = createContainer();
    private static final Map<String, GenericContainer<?>> containersByActor = new ConcurrentHashMap<>();

    private DockerManager() {
    }

    @SuppressWarnings("resource")
    private static GenericContainer<?> createContainer() {
        return new GenericContainer<>("arey/springboot-petclinic")
                .withEnv("SPRING_PROFILES_ACTIVE", "hsqldb,prod")
                .withExposedPorts(8080)
                .waitingFor(Wait.forHttp("/")
                        .forStatusCode(200)
                        .withStartupTimeout(Duration.ofMinutes(2)));
    }

    public synchronized static void startGlobalContainer() {
        LOGGER.info("Starting global container");
        if (!container.isCreated() || !container.isRunning()) {
            container.start();
        }
    }

    public synchronized static String getGlobalContainerBaseUrl() {
        startGlobalContainer();
        return getContainerBaseUrl(container);
    }

    public synchronized static void clear() {
        if (container.isRunning()) {
            LOGGER.info("Stopping global container");
            container.stop();
        }

        if (!containersByActor.isEmpty()) {
            LOGGER.info("Stopping containers used by actors");
            containersByActor.values().forEach(GenericContainer::stop);
            containersByActor.clear();
        }
    }

    public synchronized static GenericContainer<?> startContainerForActor(String actorName) {
        if (!isContainerForActorRunning(actorName)) {
            LOGGER.info("Starting container for actor: {}", actorName);
            GenericContainer<?> actorContainer = createContainer();
            actorContainer.start();
            containersByActor.put(actorName, actorContainer);
        }
        return containersByActor.get(actorName);
    }

    public synchronized static void stopContainerForActor(String actorName) {
        Optional.ofNullable(containersByActor.remove(actorName))
                .ifPresent(container -> {
                    if (container.isRunning()) {
                        LOGGER.info("Stopping container for actor: {}", actorName);
                        container.stop();
                    }
                });
    }

    private static boolean isContainerForActorRunning(String actorName) {
        return Optional.ofNullable(containersByActor.get(actorName))
                .map(GenericContainer::isRunning)
                .orElse(false);
    }

    public static String getContainerBaseUrl(GenericContainer<?> container) {
        return "http://" + container.getHost() + ":" + container.getFirstMappedPort();
    }
}