package com.ricardorlg.vetclinic.stepdefinitions;

import com.ricardorlg.vetclinc.tasks.Navigate;
import io.cucumber.java.en.Given;
import net.serenitybdd.screenplay.Actor;

public class OwnerRegistrationSteps {

    @Given("{actor} wants to register a new owner using the web application")
    public void wantsToRegisterANewOwner(Actor actor) {
        actor.attemptsTo(
            Navigate.toTheOwnerRegistrationPage()
        );
    System.out.println("OwnerRegistrationSteps: wantsToRegisterANewOwner");
    }
}
