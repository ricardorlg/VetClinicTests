package com.ricardorlg.vetclinic.stepdefinitions;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.ricardorlg.vetclinc.models.api.owners.CompleteOwnerInformation;
import com.ricardorlg.vetclinc.models.common.OwnerPersonalInformation;
import com.ricardorlg.vetclinc.models.web.OwnerRowInformation;
import com.ricardorlg.vetclinc.tasks.*;
import com.ricardorlg.vetclinc.utils.Constants;
import com.ricardorlg.vetclinc.utils.Utils;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import net.serenitybdd.core.Serenity;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.RememberThat;
import net.serenitybdd.screenplay.rest.interactions.Delete;
import net.serenitybdd.screenplay.rest.interactions.Get;
import net.serenitybdd.screenplay.rest.questions.TheResponse;
import net.serenitybdd.screenplay.waits.Wait;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import static com.ricardorlg.vetclinc.questions.CommonApiQuestions.theOwnerInResponse;
import static com.ricardorlg.vetclinc.questions.CommonApiQuestions.theOwnersInResponse;
import static com.ricardorlg.vetclinc.questions.CommonWebQuestions.theElementsOf;
import static com.ricardorlg.vetclinc.questions.OwnerQuestions.*;
import static com.ricardorlg.vetclinc.ui.OwnerPage.PETS_AND_VISITS_DATA;
import static com.ricardorlg.vetclinc.utils.EndPoints.ALL_OWNERS_PATH;
import static com.ricardorlg.vetclinc.utils.EndPoints.GET_OWNER_BY_ID_PATH;
import static com.ricardorlg.vetclinc.utils.Utils.*;
import static net.serenitybdd.screenplay.EventualConsequence.eventually;
import static net.serenitybdd.screenplay.GivenWhenThen.seeThat;
import static net.serenitybdd.screenplay.actors.OnStage.theActorInTheSpotlight;
import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.collection.IsEmptyCollection.empty;

@SuppressWarnings("unused")
public class ManageOwnersSteps {

    @Given("{pronoun} wants to filter the data of the owners displayed in the Owners page")
    @Given("{actor} wants to filter the data of the owners displayed in the Owners page")
    public void wantsToFilterTheDataOfTheOwnersDisplayedInTheOwnersPage(Actor actor) {
        actor.attemptsTo(
                RememberThat.theValueOf(Constants.USE_WEB_FORM_KEY).is(true),
                Navigates.toTheAllOwnersPage(),
                Wait.until(theDisplayedOwnersTable(), is(not(empty())))
                        .forNoMoreThan(20)
                        .seconds()
        );
    }

    @Given("{actor} wants to see the personal information of {word} {word} using the Web application")
    public void wantsToSeeThePersonalInformationOfUsingTheWebApplication(Actor actor, String ownerFirstName, String ownerLastName) {
        actor.attemptsTo(
                SearchOwnerInOwnersTable.withName(ownerFirstName, ownerLastName)
        );
    }

    @When("{pronoun} filters using the following information {string}")
    public void filtersUsingTheFollowingInformation(Actor actor, String filterInformation) {
        actor.attemptsTo(
                FilterOwnersTable.with(filterInformation)
        );
    }

    @When("{actor} sends a request to list all the owners using the clinic API")
    public void sendsARequestToListAllTheOwnersUsingTheClinicAPI(Actor actor) {
        actor.attemptsTo(
                Get.resource(ALL_OWNERS_PATH.getPath())
        );
    }

    @When("{pronoun} selects the owner in the Owners page")
    public void selectsTheOwnerInTheOwnersPage(Actor actor) {
        String ownerToSelect = actor.recall(Constants.OWNER_TO_SELECT);
        actor.attemptsTo(
                SelectFromOwnersTable.theOwnerWithFullName(ownerToSelect)
        );
    }

    @When("{actor} sends a request to see all the information of  the owner with ID {int} using the clinic API")
    public void sendsARequestToSeeAllTheInformationOfTheOwnerWithID1UsingTheClinicAPI(Actor actor, int ownerId) {
        actor.attemptsTo(
                Get.resource(GET_OWNER_BY_ID_PATH.getPath())
                        .with(request -> request.pathParam("ownerId", ownerId)),
                RememberThat.theValueOf(Constants.FETCHED_OWNER_ID).is(ownerId)
        );
    }

    @When("{actor} updates the personal information of {word} {word} using the Web application to")
    public void updatesThePersonalInformationOfUsingTheWebApplicationTo(Actor actor, String ownerFirstName, String ownerLastName, OwnerPersonalInformation updatedOwnerInformation) {
        actor.attemptsTo(
                UpdateOwnerPersonalInformation.with(ownerFirstName, ownerLastName, updatedOwnerInformation)
        );
        actorTakesTheSpotlight(actor);
    }

    @When("{actor} sends a request to delete {word} {word} using the clinic API")
    public void sendsARequestToDeleteUsingTheClinicAPI(Actor actor, String ownerFirstName, String ownerLastName) {
        var ownerId = actor.asksFor(theRegisteredOwnersInTheSystem())
                .stream()
                .filter(owner -> owner.firstName().equals(ownerFirstName) && owner.lastName().equals(ownerLastName))
                .findFirst()
                .orElseThrow(() -> new IllegalStateException("The owner " + ownerFirstName + " " + ownerLastName + " is not registered in the system"))
                .id();
        actor.attemptsTo(
                Delete.from(GET_OWNER_BY_ID_PATH.getPath())
                        .with(request -> request.pathParam("ownerId", ownerId)),
                RememberThat.theValueOf(Constants.FETCHED_OWNER_ID).is(ownerId)
        );
        actorTakesTheSpotlight(actor);
    }

    @Then("{pronoun} should see the following owners in the table")
    public void shouldSeeTheFollowingOwnersInTheTable(Actor actor, List<OwnerPersonalInformation> expectedOwnersInformation) {
        var expectedOwnersRows = expectedOwnersInformation.stream()
                .map(OwnerPersonalInformation::toOwnerRowInformation)
                .toList();
        var simpleExpectedOwnersInformation = expectedOwnersRows.stream()
                .map(OwnerRowInformation::name)
                .collect(Collectors.joining(", "));

        actor.should(
                eventually(seeThat(theDisplayedOwnersTable(), hasSize(expectedOwnersRows.size())))
                        .waitingForNoLongerThan(10)
                        .seconds(),
                seeThat(theDisplayedOwnersTable(), containsInAnyOrder(expectedOwnersRows.toArray()))
                        .because("Then %s should include only the information of: " + simpleExpectedOwnersInformation)
        );
    }

    @Then("{pronoun} should see no owners")
    public void shouldSeeNoOwners(Actor actor) {
        actor.should(
                eventually(
                        seeThat(theDisplayedOwnersTable(), is(empty()))
                )
                        .waitingForNoLongerThan(20)
                        .seconds()
        );
    }

    @Then("the response body should include the default owners registered in the system")
    public void theResponseBodyShouldIncludeTheDefaultOwnersRegisteredInTheSystem() throws IOException {
        List<CompleteOwnerInformation> defaultOwners = getDefaultOwnersData();
        Serenity.recordReportData()
                .withTitle("Default Owners")
                .andContents(prettyPrint(defaultOwners));

        theActorInTheSpotlight()
                .should(
                        seeThat(theOwnersInResponse(), hasItems(defaultOwners.toArray(CompleteOwnerInformation[]::new)))
                                .because("Then %s should contain the default owners registered in the system")
                );
    }

    @Then("{pronoun} should see the owner information registered by {actor}")
    public void shouldSeeTheInformationRegisteredByForTheOwner(Actor whoIsSeeing, Actor actorWhoRegistered) {
        List<OwnerPersonalInformation> registeredOwner = actorWhoRegistered.recall(Constants.REGISTERED_OWNERS);
        var expectedOwnerInformation = registeredOwner.getFirst().toOwnerPageCompleteInformation();
        actorTakesTheSpotlight(whoIsSeeing);
        whoIsSeeing.should(
                eventually(
                        seeThat(theDisplayedOwnerInformation(), equalTo(expectedOwnerInformation))
                                .because("Then %s should contain the information of " + expectedOwnerInformation.ownerFullName())
                ).waitingForNoLongerThan(20)
                        .seconds()
        );

    }

    @Then("the pets and visits information should be empty")
    public void thePetsAndVisitsInformationShouldBeEmpty() {
        theActorInTheSpotlight().should(
                eventually(seeThat(theElementsOf(PETS_AND_VISITS_DATA), is(empty())))
                        .waitingForNoLongerThan(5)
                        .seconds()
        );
    }

    @Then("{pronoun} should see the registered information of {word} {word}")
    public void shouldSeeTheRegisteredInformationOf(Actor actor, String ownerFirstName, String ownerLastName) throws JsonProcessingException {
        var expectedOwnerInformation = actor.asksFor(theRegisteredOwnersInTheSystem())
                .stream()
                .filter(owner -> owner.firstName().equals(ownerFirstName) && owner.lastName().equals(ownerLastName))
                .findFirst()
                .orElseThrow(() -> new IllegalStateException("The owner " + ownerFirstName + " " + ownerLastName + " is not registered in the system"))
                .toOwnerPageCompleteInformation();

        Serenity.recordReportData()
                .withTitle("Expected Owner Information")
                .andContents(Utils.prettyPrint(expectedOwnerInformation));

        actor.should(
                eventually(
                        seeThat(theDisplayedOwnerInformation(), equalTo(expectedOwnerInformation))
                                .because("Then %s should contain the information of " + expectedOwnerInformation.ownerFullName())
                ).waitingForNoLongerThan(20)
                        .seconds()
        );
    }

    @Then("the response body should include all the registered information of the owner including the pets and visits")
    public void theResponseBodyShouldIncludeAllTheRegisteredInformationOfTheOwnerIncludingThePetsAndVisits() {
        int fetchedOwnerId = theActorInTheSpotlight().recall(Constants.FETCHED_OWNER_ID);
        var expectedOwnerInformation = getDefaultOwnersData()
                .stream()
                .filter(owner -> owner.id() == fetchedOwnerId)
                .findFirst()
                .orElseThrow(() -> new IllegalStateException("The owner with ID " + fetchedOwnerId + " is not registered in the system"));
        theActorInTheSpotlight()
                .should(seeThat(theOwnerInResponse(), equalTo(expectedOwnerInformation))
                        .because("Then %s should contain the information of " + expectedOwnerInformation.fullName())
                );
    }

    @Then("the owner should be updated successfully")
    public void theOwnerShouldBeUpdatedSuccessfully() {
        int updatedOwnerId = theActorInTheSpotlight().recall(Constants.FETCHED_OWNER_ID);
        CompleteOwnerInformation updatedOwnerInformation = theActorInTheSpotlight()
                .<OwnerPersonalInformation>recall(Constants.REGISTERED_OWNER_INFORMATION)
                .toCompleteOwnerInformation(updatedOwnerId);

        theActorInTheSpotlight()
                .should(
                        eventually(seeThat(theDetailsOfTheOwnerWithId(updatedOwnerId), is(equalTo(updatedOwnerInformation)))
                                .because("Then %s should contain the updated information of the owner  " + updatedOwnerInformation.fullName())
                        ).waitingForNoLongerThan(20)
                                .seconds()
                );
    }

    @Then("the system should display the updated information")
    public void theSystemShouldDisplayTheUpdatedInformation() {
        var updatedOwnerInformation = theActorInTheSpotlight()
                .<OwnerPersonalInformation>recall(Constants.REGISTERED_OWNER_INFORMATION)
                .toOwnerPageCompleteInformation();

        theActorInTheSpotlight().should(
                eventually(
                        seeThat(theDisplayedOwnerInformation(), equalTo(updatedOwnerInformation))
                                .because("Then %s should contain the information of " + updatedOwnerInformation.ownerFullName())
                ).waitingForNoLongerThan(10)
                        .seconds()
        );
    }

    @Then("the owner should not be listed in the system anymore")
    public void theOwnerShouldNotBeListedInTheSystemAnymore() {
        int deletedOwnerId = theActorInTheSpotlight().recall(Constants.FETCHED_OWNER_ID);
        theActorInTheSpotlight().should(
                eventually(
                        seeThat(TheResponse.statusCode(), equalTo(404))
                                .after(Get.resource(GET_OWNER_BY_ID_PATH.getPath())
                                        .with(request -> request.pathParam("ownerId", deletedOwnerId))
                                        .withNoReporting()
                                ).because("Then %s should not contain the information of the owner with ID " + deletedOwnerId)
                ).waitingForNoLongerThan(20)
                        .seconds()
        );
    }
}
