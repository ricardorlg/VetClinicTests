package com.ricardorlg.vetclinic.stepdefinitions;

import com.ricardorlg.vetclinc.models.api.pets.CompletePetsInformation;
import com.ricardorlg.vetclinc.models.common.PetInformation;
import com.ricardorlg.vetclinc.tasks.Navigates;
import com.ricardorlg.vetclinc.tasks.RegisterPet;
import com.ricardorlg.vetclinc.tasks.RegisterPets;
import com.ricardorlg.vetclinc.tasks.UpdatesTheInformationOfThePet;
import com.ricardorlg.vetclinc.ui.PetPage;
import com.ricardorlg.vetclinc.utils.Constants;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Forget;
import net.serenitybdd.screenplay.RememberThat;
import net.serenitybdd.screenplay.actions.Click;
import net.serenitybdd.screenplay.conditions.Check;
import net.serenitybdd.screenplay.questions.SelectOptions;
import net.serenitybdd.screenplay.questions.TheMemory;
import org.assertj.core.api.Assertions;
import org.assertj.core.api.recursive.comparison.RecursiveComparisonConfiguration;

import java.util.List;
import java.util.stream.Collectors;

import static com.ricardorlg.vetclinc.questions.OwnerQuestions.theDetailsOfTheOwnerWithId;
import static com.ricardorlg.vetclinc.questions.OwnerQuestions.theDisplayedPetsAndVisitsInformation;
import static net.serenitybdd.screenplay.EventualConsequence.eventually;
import static net.serenitybdd.screenplay.GivenWhenThen.seeThat;
import static net.serenitybdd.screenplay.actors.OnStage.theActorInTheSpotlight;
import static org.hamcrest.Matchers.*;

@SuppressWarnings("unused")
public class PetsManagementSteps {

    @Given("{actor} wants to register a new pet for {word} {word} using the Web application")
    public void wantsToRegisterANewPetForAnOwner(Actor actor, String ownerFirstName, String ownerLastName) {
        actor.attemptsTo(
                RememberThat.theValueOf(Constants.USE_WEB_FORM_KEY).is(true),
                Navigates.toTheAddNewPetPageOf(ownerFirstName, ownerLastName)
        );
    }



    @Given("{pronoun} has registered the following pet(s) for {word} {word}")
    public void hasRegisteredTheFollowingPetsFor(Actor actor, String firstName, String lastName, List<PetInformation> pets) {
        pets.forEach(petInformation -> actor.wasAbleTo(
                Check.whether(TheMemory.withKey(Constants.USE_WEB_FORM_KEY).isPresent())
                        .andIfSo(
                                Forget.theValueOf(Constants.USE_WEB_FORM_KEY),
                                RegisterPet
                                        .forOwner(firstName, lastName)
                                        .withPetInformation(petInformation)
                                        .withNoReporting(),
                                RememberThat.theValueOf(Constants.USE_WEB_FORM_KEY).is(true)
                        ).otherwise(
                                RegisterPet
                                        .forOwner(firstName, lastName)
                                        .withPetInformation(petInformation)
                                        .withNoReporting()
                        )
        ));
        actor.remember(Constants.LATEST_REGISTERED_PET_OWNER_NAME, firstName + " " + lastName);

    }

    @When("{pronoun} registers the following pet for {word} {word} using the Web application")
    public void registersTheFollowingPetFor(Actor actor, String firstName, String lastName, PetInformation petInformation) {
        actor.attemptsTo(
                RememberThat.theValueOf(Constants.USE_WEB_FORM_KEY).is(true),
                RegisterPet
                        .forOwner(firstName, lastName)
                        .withPetInformation(petInformation)
        );
    }

    @When("{pronoun} registers the following pets for {word} {word} using the Web application")
    public void registersTheFollowingPetsFor(Actor actor, String firstName, String lastName, List<PetInformation> pets) {
        actor.attemptsTo(
                RememberThat.theValueOf(Constants.USE_WEB_FORM_KEY).is(true),
                RegisterPets.forOwner(firstName, lastName, pets)
        );
    }

    @When("{pronoun} sends a request to register the following pet for {word} {word} using the clinic API")
    public void sendsARequestToRegisterTheFollowingPetFor(Actor actor, String firstName, String lastName, PetInformation petInformation) {
        actor.attemptsTo(
                Forget.theValueOf(Constants.USE_WEB_FORM_KEY),
                RegisterPet
                        .forOwner(firstName, lastName)
                        .withPetInformation(petInformation)
        );
    }

    @When("{pronoun} updates the information of the pet using the Web application to")
    public void updatesTheInformationOfThePetUsingTheWebApplicationTo(Actor actor, PetInformation updatedPetInformation) {
        PetInformation petToUpdate = theActorInTheSpotlight().recall(Constants.LATEST_REGISTERED_PET);
        actor.attemptsTo(
                RememberThat.theValueOf(Constants.USE_WEB_FORM_KEY).is(true),
                UpdatesTheInformationOfThePet
                        .petToUpdate(petToUpdate)
                        .with(updatedPetInformation)
        );
    }

    @Then("the pet information should be displayed in the pets and visits section")
    public void thePetInformationShouldBeDisplayedInThePetsAndVisitsSection() {
        PetInformation registeredPet = theActorInTheSpotlight().recall(Constants.LATEST_REGISTERED_PET);
        theActorInTheSpotlight().should(
                eventually(
                        seeThat(theDisplayedPetsAndVisitsInformation(), hasItem(registeredPet.toPetsAndVisitsInformationWithNoVisits()))
                                .because("Then %s should contain the information of " + registeredPet.name())
                ).waitingForNoLongerThan(5)
                        .seconds()
        );
    }

    @Then("the pets  should be displayed in the owner information page")
    public void thePetsShouldBeDisplayedInTheOwnerInformationPage() {
        var expected = theActorInTheSpotlight().<List<PetInformation>>recall(Constants.REGISTERED_PETS)
                .stream()
                .map(PetInformation::toPetsAndVisitsInformationWithNoVisits)
                .toList()
                .reversed();
        var expectedPetNames = expected
                .stream()
                .map(petsAndVisitsInformation -> petsAndVisitsInformation.petInformation().name())
                .collect(Collectors.joining(", "));
        theActorInTheSpotlight().should(
                eventually(seeThat(theDisplayedPetsAndVisitsInformation(), containsInAnyOrder(expected.toArray()))
                        .because("Then %s should contain the information of  " + expectedPetNames)
                ).waitingForNoLongerThan(5)
                        .seconds()
        );
    }

    @Then("she should see the following types of pets available")
    public void sheShouldSeeTheFollowingTypesOfPetsAvailable(List<String> expectedTypes) {
        theActorInTheSpotlight()
                .should(
                        eventually(
                                seeThat(SelectOptions.of(PetPage.PET_TYPE_SELECT), containsInAnyOrder(expectedTypes.toArray()))
                                        .after(Click.on(PetPage.PET_TYPE_SELECT))
                        ).waitingForNoLongerThan(10)
                                .seconds()
                );
    }

    @Then("the information of the pet should be added to the owner information")
    public void theInformationOfThePetShouldBeAddedToTheOwnerInformation() {
        int ownerId = theActorInTheSpotlight().recall(Constants.FETCHED_OWNER_ID);
        CompletePetsInformation registeredPet = theActorInTheSpotlight().recall(Constants.FULL_PET_INFORMATION_REGISTERED_WITH_API);
        var ownerDetails = theActorInTheSpotlight().asksFor(theDetailsOfTheOwnerWithId(ownerId));
        RecursiveComparisonConfiguration configuration = RecursiveComparisonConfiguration.builder()
                .withIgnoredFields("id", "visits.id")
                .build();
        Assertions.assertThat(ownerDetails.pets())
                .usingRecursiveFieldByFieldElementComparator(configuration)
                .contains(registeredPet);

    }

    @Then("the updated pet information should be displayed in the pets and visits section")
    public void thePetInformationShouldBeUpdatedSuccessfully() {
        PetInformation oldPetInformation = theActorInTheSpotlight().recall(Constants.OLD_PET_INFORMATION);
        PetInformation updatedPetInformation = theActorInTheSpotlight().recall(Constants.UPDATED_PET_INFORMATION);
        theActorInTheSpotlight().should(
                eventually(seeThat(theDisplayedPetsAndVisitsInformation(), hasItem(updatedPetInformation.toPetsAndVisitsInformationWithNoVisits()))
                        .because("Then %s should contain the information of " + updatedPetInformation.name())
                ).waitingForNoLongerThan(5)
                        .seconds(),
                seeThat(theDisplayedPetsAndVisitsInformation(), not(hasItem(oldPetInformation.toPetsAndVisitsInformationWithNoVisits())))
        );
    }
}
