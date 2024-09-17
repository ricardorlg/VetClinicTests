package com.ricardorlg.vetclinic.stepdefinitions;

import com.ricardorlg.vetclinc.models.api.visits.CompleteVisitInformation;
import com.ricardorlg.vetclinc.models.common.VisitInformation;
import com.ricardorlg.vetclinc.tasks.RegisterVisit;
import com.ricardorlg.vetclinc.ui.OwnerPage;
import com.ricardorlg.vetclinc.utils.Constants;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.RememberThat;
import net.serenitybdd.screenplay.actions.Click;
import net.serenitybdd.screenplay.waits.WaitUntil;

import static com.ricardorlg.vetclinc.questions.OwnerQuestions.theDetailsOfTheOwnerWithId;
import static com.ricardorlg.vetclinc.questions.OwnerQuestions.theDisplayedVisitsOf;
import static com.ricardorlg.vetclinc.questions.VetClinicQuestions.theDisplayedPreviousVisitsOf;
import static com.ricardorlg.vetclinc.utils.Constants.LATEST_ADDED_VISIT_PET_NAME;
import static net.serenitybdd.screenplay.EventualConsequence.eventually;
import static net.serenitybdd.screenplay.GivenWhenThen.seeThat;
import static net.serenitybdd.screenplay.actors.OnStage.theActorInTheSpotlight;
import static net.serenitybdd.screenplay.matchers.WebElementStateMatchers.isVisible;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.hasItem;

@SuppressWarnings("unused")
public class VisitsManagementSteps {

    @When("{pronoun} registers the following visit for {word} using the Web application")
    public void registersTheFollowingVisitFor(Actor actor, String petName, VisitInformation visitInformation) {
        actor.attemptsTo(
                RegisterVisit
                        .forPet(petName)
                        .withInformation(visitInformation),
                RememberThat.theValueOf(LATEST_ADDED_VISIT_PET_NAME).is(petName)
        );
    }

    @And("{pronoun} has registered the following visit for {word} using the Web application")
    public void hasRegisteredTheFollowingVisitFor(Actor actor, String petName, VisitInformation visitInformation) {
        actor.wasAbleTo(
                RegisterVisit
                        .forPet(petName)
                        .withInformation(visitInformation)
                        .withNoReporting(),
                RememberThat.theValueOf(LATEST_ADDED_VISIT_PET_NAME).is(petName)
        );
    }

    @When("{pronoun} goes to the Register Visit for the pet named {word}")
    public void goesToTheRegisterVisitForThePetNamed(Actor actor, String petName) {
        actor.attemptsTo(
                WaitUntil.the(OwnerPage.PET_WITH_NAME_LINK.of(petName), isVisible())
                        .forNoMoreThan(10)
                        .seconds(),
                Click.on(OwnerPage.ADD_VISIT_FOR_PET_BUTTON.of(petName).called("the Add Visit button for " + petName))
        );
    }

    @Then("the visit should be displayed in the owner information page")
    public void theVisitShouldBeDisplayedInTheOwnerInformationPage() {
        String registeredPet = theActorInTheSpotlight().recall(LATEST_ADDED_VISIT_PET_NAME);
        VisitInformation registeredVisit = theActorInTheSpotlight().recall(Constants.LATEST_REGISTERED_VISIT_INFORMATION);
        theActorInTheSpotlight()
                .should(
                        eventually(
                                seeThat(
                                        theDisplayedVisitsOf(registeredPet), hasItem(registeredVisit)
                                )
                        ).waitingForNoLongerThan(10)
                                .seconds()
                );
    }

    @Then("{pronoun} should see all the previous visits made by {word}")
    public void shouldSeeAllThePreviousVisitsMadeBy(Actor actor, String petName) {
        int ownerID = actor.recall(Constants.FETCHED_OWNER_ID);
        var visitsMadeByPet = actor.asksFor(theDetailsOfTheOwnerWithId(ownerID))
                .pets()
                .stream()
                .filter(pet -> pet.name().equalsIgnoreCase(petName))
                .findFirst()
                .orElseThrow(() -> new IllegalStateException("The pet %s was not found in the owner information".formatted(petName)))
                .visits()
                .stream()
                .map(CompleteVisitInformation::toVisitInformation)
                .toList();

        actor.should(
                eventually(
                        seeThat(
                                theDisplayedPreviousVisitsOf(petName), containsInAnyOrder(visitsMadeByPet.toArray())
                        )
                ).waitingForNoLongerThan(10)
                        .seconds()
        );
    }
}
