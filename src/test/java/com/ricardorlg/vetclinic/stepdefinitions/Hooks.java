package com.ricardorlg.vetclinic.stepdefinitions;

import com.ricardorlg.vetclinc.utils.DockerManager;
import com.ricardorlg.vetclinic.actors.VetClinicCast;
import io.cucumber.java.*;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import net.serenitybdd.core.Serenity;
import net.serenitybdd.model.environment.EnvironmentSpecificConfiguration;
import net.serenitybdd.rest.SerenityRest;
import net.serenitybdd.screenplay.actors.OnStage;

@SuppressWarnings("unused")
public class Hooks {


    @BeforeAll
    public synchronized static void globalSetup() {
        var withRestLogging = EnvironmentSpecificConfiguration.from(Serenity.environmentVariables())
                .getBooleanProperty("enable.rest.logging");
        if (withRestLogging) {
            SerenityRest.filters(new RequestLoggingFilter(), new ResponseLoggingFilter());
        }
        DockerManager.startGlobalContainer();
    }

    @Before(value = "not @withFeatureLevelContainer and not @withScenarioLevelContainer", order = 1)
    public void beforeScenario(Scenario scenario) {
        OnStage.setTheStage(new VetClinicCast(scenario));
    }

    @Before(value = "@withFeatureLevelContainer and not @withScenarioLevelContainer", order = 2)
    public void beforeScenarioWithFeatureLevelContainer(Scenario scenario) {
        OnStage.setTheStage(new VetClinicCast(scenario,false, true));
    }

    @After
    public void afterScenario() {
        OnStage.drawTheCurtain();
    }

    @AfterAll
    public static void globalTearDown() {
        DockerManager.clear();
    }

}
