package com.ricardorlg.vetclinic.stepdefinitions;

import io.cucumber.java.ParameterType;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.actors.OnStage;

public class ParameterTypes {
    @ParameterType("[A-Z][a-z]+")
    public Actor actor(String actor) {
        return OnStage.theActorCalled(actor);
    }

    @ParameterType("(?i)he|she|they|his|her|their")
    public Actor pronoun(String pronoun) {
        return OnStage.theActorInTheSpotlight();
    }
}
