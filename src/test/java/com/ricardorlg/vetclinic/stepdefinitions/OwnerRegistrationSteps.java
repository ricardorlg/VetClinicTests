package com.ricardorlg.vetclinic.stepdefinitions;

import com.ricardorlg.vetclinc.tasks.Navigate;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import net.serenitybdd.screenplay.Actor;

public class OwnerRegistrationSteps {

    @Given("{actor} wants to register a new owner using the web application")
    public void wantsToRegisterANewOwner(Actor actor) {
        actor.attemptsTo(
                Navigate.toTheOwnerRegistrationPage()
        );
    }

    @When("{pronoun} fills the registration form with the following information")
    public void fillsTheOwnerRegistrationForm(Actor actor, DataTable table) {
        //TODO: Implement the step definition
    }

    @Then("the owner should be registered successfully")
    public void theOwnerShouldBeRegisteredSuccessfully() {
        //TODO: Implement the step definition
    }
}
