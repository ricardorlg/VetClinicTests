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
    private static final Map<String, GenericContainer<?>> containersByScenario = new ConcurrentHashMap<>();

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

        if (!containersByScenario.isEmpty()) {
            LOGGER.info("Stopping containers used by scenarios");
            containersByScenario.values().forEach(GenericContainer::stop);
            containersByScenario.clear();
        }
    }

    public synchronized static GenericContainer<?> startContainerForScenario(String scenarioName) {
        if (!isContainerForScenarioRunning(scenarioName)) {
            LOGGER.info("Starting container for scenario: {}", scenarioName);
            GenericContainer<?> scenarioContainer = createContainer();
            scenarioContainer.start();
            containersByScenario.put(scenarioName, scenarioContainer);
        }
        return containersByScenario.get(scenarioName);
    }

    public synchronized static void stopContainerForScenario(String scenarioName) {
        Optional.ofNullable(containersByScenario.remove(scenarioName))
                .ifPresent(container -> {
                    if (container.isRunning()) {
                        container.stop();
                    }
                });
    }

    private static boolean isContainerForScenarioRunning(String scenarioName) {
        return Optional.ofNullable(containersByScenario.get(scenarioName))
                .map(GenericContainer::isRunning)
                .orElse(false);
    }

    public static String getContainerBaseUrl(GenericContainer<?> container) {
        return "http://" + container.getHost() + ":" + container.getFirstMappedPort();
    }
}