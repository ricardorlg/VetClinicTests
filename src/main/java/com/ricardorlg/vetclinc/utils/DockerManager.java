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
    private static final Map<String, GenericContainer<?>> containerMap = new ConcurrentHashMap<>();

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
        if (!container.isCreated() || !container.isRunning()) {
            LOGGER.info("Starting global container");
            container.start();
            LOGGER.info("Global container started");
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

        if (!containerMap.isEmpty()) {
            LOGGER.info("Stopping containers used by tests");
            containerMap.values().forEach(GenericContainer::stop);
            containerMap.clear();
        }
    }

    public synchronized static GenericContainer<?> startContainerFor(String identifier) {
        if (!isContainerForKeyRunning(identifier)) {
            LOGGER.info("Starting container for: {}", identifier);
            GenericContainer<?> container = createContainer();
            container.start();
            containerMap.put(identifier, container);
            LOGGER.info("Container for: {} started", identifier);
        }
        return containerMap.get(identifier);
    }

    public synchronized static void stopContainerFor(String identifier) {
        Optional.ofNullable(containerMap.remove(identifier))
                .ifPresent(container -> {
                    if (container.isRunning()) {
                        LOGGER.info("Stopping container for: {}", identifier);
                        container.stop();
                    }
                });
    }

    private static boolean isContainerForKeyRunning(String key) {
        return Optional.ofNullable(containerMap.get(key))
                .map(GenericContainer::isRunning)
                .orElse(false);
    }

    public static String getContainerBaseUrl(GenericContainer<?> container) {
        return "http://" + container.getHost() + ":" + container.getFirstMappedPort();
    }
}