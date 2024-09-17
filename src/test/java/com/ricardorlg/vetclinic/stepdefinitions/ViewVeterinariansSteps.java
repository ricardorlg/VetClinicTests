package com.ricardorlg.vetclinic.stepdefinitions;

import com.ricardorlg.vetclinc.models.common.VeterinarianInformation;
import com.ricardorlg.vetclinc.tasks.Navigates;
import com.ricardorlg.vetclinc.ui.WelcomePage;
import com.ricardorlg.vetclinc.utils.Constants;
import com.ricardorlg.vetclinc.utils.EndPoints;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.RememberThat;
import net.serenitybdd.screenplay.actions.Click;
import net.serenitybdd.screenplay.rest.interactions.Get;

import java.util.List;
import java.util.stream.Collectors;

import static com.ricardorlg.vetclinc.questions.CommonApiQuestions.theVeterinariansInResponse;
import static com.ricardorlg.vetclinc.questions.VetClinicQuestions.theDisplayedVeterinarians;
import static com.ricardorlg.vetclinc.utils.Utils.getDefaultVeterinariansData;
import static net.serenitybdd.screenplay.EventualConsequence.eventually;
import static net.serenitybdd.screenplay.GivenWhenThen.seeThat;
import static net.serenitybdd.screenplay.actors.OnStage.theActorInTheSpotlight;
import static org.hamcrest.Matchers.containsInAnyOrder;

@SuppressWarnings("unused")
public class ViewVeterinariansSteps {

    @Given("{actor} is using the Pet Clinic Web application")
    public void isUsingThePetClinicWebApplication(Actor actor) {
        actor.attemptsTo(
                RememberThat.theValueOf(Constants.USE_WEB_FORM_KEY).is(true),
                Navigates.toTheWelcomePage()
        );
    }

    @When("{pronoun} opens the veterinarians page")
    public void opensTheVeterinariansPage(Actor actor) {
        actor.attemptsTo(
                Click.on(WelcomePage.VETERINARIANS_MENU)
        );
    }

    @When("{actor} sends a request to get the list of veterinarians")
    public void sendsARequestToGetTheListOfVeterinarians(Actor actor) {
        actor.attemptsTo(
                Get.resource(EndPoints.VETERINARIANS.getPath())
        );
    }

    @Then("{pronoun} should see the following veterinarians information")
    public void shouldSeeTheFollowingVeterinariansInformation(Actor actor, List<VeterinarianInformation> veterinarians) {
        var veterinariansNames = veterinarians.stream().map(VeterinarianInformation::name).collect(Collectors.joining(", "));
        actor.should(
                eventually(
                        seeThat(theDisplayedVeterinarians(), containsInAnyOrder(veterinarians.toArray()))
                                .because("Then %s should contain exactly the information of " + veterinariansNames)
                ).waitingForNoLongerThan(10).seconds()
        );
    }

    @Then("the response body should include the default veterinarians information")
    public void theResponseBodyShouldIncludeTheDefaultVeterinariansInformation() {
        var defaultVeterinariansData = getDefaultVeterinariansData();
        theActorInTheSpotlight()
                .should(
                        seeThat(theVeterinariansInResponse(), containsInAnyOrder(defaultVeterinariansData.toArray()))
                                .because("Then %s should contain exactly the default veterinarians information")
                );
    }
}
