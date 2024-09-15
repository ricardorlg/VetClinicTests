package com.ricardorlg.vetclinic.stepdefinitions;

import com.ricardorlg.vetclinc.models.common.OwnerPersonalInformation;
import io.cucumber.java.DataTableType;
import io.cucumber.java.ParameterType;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.actors.OnStage;

import java.util.Map;

@SuppressWarnings("unused")
public class ParameterTypes {
    @ParameterType("[A-Z][a-z]+")
    public Actor actor(String actor) {
        return OnStage.theActorCalled(actor);
    }

    @ParameterType("(?i)he|she|they|his|her|their")
    public Actor pronoun(String pronoun) {
        return OnStage.theActorInTheSpotlight();
    }

    @DataTableType(replaceWithEmptyString = "[empty]")
    public OwnerPersonalInformation ownerPersonalInformationTransformer(Map<String, String> entry) {
        return new OwnerPersonalInformation(
                parseEntry(entry.get("firstName")),
                parseEntry(entry.get("lastName")),
                parseEntry(entry.get("address")),
                parseEntry(entry.get("city")),
                parseEntry(entry.get("phone"))
        );
    }

    private String parseEntry(String entry) {
        if (entry.equals("[blank]")) {
            return "  ";
        }
        return entry;
    }
}
