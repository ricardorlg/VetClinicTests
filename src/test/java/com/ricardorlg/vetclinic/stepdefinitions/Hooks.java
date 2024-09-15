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

public class Hooks {


    @BeforeAll
    public static void globalSetup() {
        var withRestLogging = EnvironmentSpecificConfiguration.from(Serenity.environmentVariables())
                .getBooleanProperty("enable.rest.logging");
        DockerManager.startGlobalContainer();
        if (withRestLogging) {
            SerenityRest.filters(new RequestLoggingFilter(), new ResponseLoggingFilter());
        }
    }


    @Before(value = "not @Isolated", order = 1)
    public void setStageWithGlobalContainer(Scenario scenario) {
        OnStage.setTheStage(new VetClinicCast(false));
    }

    @Before(value = "@Isolated", order = 2)
    public void setIsolatedStage() {
        OnStage.setTheStage(new VetClinicCast(true));
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
