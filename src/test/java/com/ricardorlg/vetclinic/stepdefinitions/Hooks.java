package com.ricardorlg.vetclinic.stepdefinitions;

import com.ricardorlg.vetclinc.utils.DockerManager;
import com.ricardorlg.vetclinic.actors.VetClinicCast;
import io.cucumber.java.*;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.actors.OnStage;

public class Hooks {


    @BeforeAll
    public static void globalSetup() {
        DockerManager.startGlobalContainer();
    }


    @Before(value = "not @Isolated and not @WithServerDown", order = 1)
    public void setStageWithGlobalContainer(Scenario scenario) {
        OnStage.setTheStage(new VetClinicCast(scenario, false));
    }

    @Before(value = "@Isolated and not @WithServerDown", order = 2)
    public void setIsolatedStage(Scenario scenario) {
        OnStage.setTheStage(new VetClinicCast(scenario, true));
    }

    @After
    public void afterScenario(Scenario scenario) {
        OnStage.drawTheCurtain();
        DockerManager.stopContainerForScenario(scenario.getName());
    }

    @AfterAll
    public static void globalTearDown() {
        DockerManager.clear();
    }

}
