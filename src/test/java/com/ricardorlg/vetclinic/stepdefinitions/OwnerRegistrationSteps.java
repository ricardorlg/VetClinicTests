package com.ricardorlg.vetclinic.stepdefinitions;

import com.ricardorlg.vetclinc.models.common.OwnerPersonalInformation;
import com.ricardorlg.vetclinc.tasks.Navigate;
import com.ricardorlg.vetclinc.tasks.RegisterOwner;
import com.ricardorlg.vetclinc.utils.Constants;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.RememberThat;
import net.serenitybdd.screenplay.actors.OnStage;

import static com.ricardorlg.vetclinc.questions.OwnerQuestions.theDisplayedOwnersTable;
import static net.serenitybdd.screenplay.EventualConsequence.eventually;
import static net.serenitybdd.screenplay.GivenWhenThen.seeThat;
import static net.serenitybdd.screenplay.actors.OnStage.theActorInTheSpotlight;
import static org.hamcrest.Matchers.hasItem;

@SuppressWarnings("unused")
public class OwnerRegistrationSteps {

    @Given("{actor} wants to register a new owner using the web application")
    public void wantsToRegisterANewOwner(Actor actor) {
        actor.attemptsTo(
                Navigate.toTheOwnerRegistrationPage(),
                RememberThat.theValueOf(Constants.USE_WEB_FORM_KEY).is(true)
        );
    }

    @When("{pronoun} fills the registration form with the following information")
    public void fillsTheOwnerRegistrationForm(Actor actor, OwnerPersonalInformation ownerPersonalInformation) {
        actor.attemptsTo(
                RegisterOwner.withInformation(ownerPersonalInformation),
                RememberThat.theValueOf(Constants.REGISTERED_OWNER_INFORMATION).is(ownerPersonalInformation)
        );
    }

    @When("{actor} sends a registration request using the clinic API with the following data")
    @Given("{actor} has already registered an owner with the following information")
    public void sendsARegistrationRequestUsingTheClinicAPI(Actor actor, OwnerPersonalInformation ownerPersonalInformation) {
        actor.attemptsTo(
                RegisterOwner.withInformation(ownerPersonalInformation),
                RememberThat.theValueOf(Constants.REGISTERED_OWNER_INFORMATION).is(ownerPersonalInformation)
        );
    }

    @When("{pronoun} fills the registration form with the same data used by {actor}")
    @When("{actor} sends a registration request using the clinic API with the same data used by {actor}")
    public void fillsTheRegistrationFormWithTheSameDataUsedByAnotherActor(Actor whoWillPerform, Actor whoHasRegisteredTheOwner) {
        var ownerInformation = whoHasRegisteredTheOwner
                .<OwnerPersonalInformation>recall(Constants.REGISTERED_OWNER_INFORMATION);
        whoWillPerform.attemptsTo(
                RegisterOwner.withInformation(ownerInformation)
        );
        OnStage.theActorCalled(whoWillPerform.getName()).entersTheScene();
    }

    @Then("the owner should be registered successfully")
    public void theOwnerShouldBeRegisteredSuccessfully() {
        var expectedOwner = theActorInTheSpotlight()
                .<OwnerPersonalInformation>recall(Constants.REGISTERED_OWNER_INFORMATION)
                .toOwnerRowInformation();
        theActorInTheSpotlight()
                .should(
                        eventually(
                                seeThat(theDisplayedOwnersTable(), hasItem(expectedOwner))
                                        .because("Then %s should contain the information of " + expectedOwner.name())
                        ).waitingForNoLongerThan(20)
                                .seconds()
                );
    }
}
