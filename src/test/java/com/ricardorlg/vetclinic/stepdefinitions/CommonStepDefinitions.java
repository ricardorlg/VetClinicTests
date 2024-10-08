package com.ricardorlg.vetclinic.stepdefinitions;

import com.ricardorlg.vetclinc.facts.RegisteredOwners;
import com.ricardorlg.vetclinc.models.common.OwnerPersonalInformation;
import com.ricardorlg.vetclinc.tasks.Navigates;
import com.ricardorlg.vetclinc.utils.Constants;
import io.cucumber.docstring.DocString;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import net.serenitybdd.core.Serenity;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.RememberThat;
import net.serenitybdd.screenplay.rest.questions.TheResponse;

import java.util.List;

import static com.ricardorlg.vetclinc.questions.CommonApiQuestions.theErrorsInResponse;
import static com.ricardorlg.vetclinc.questions.CommonApiQuestions.theIdOfTheOwner;
import static com.ricardorlg.vetclinc.questions.CommonWebQuestions.theDisplayedAlertErrorContent;
import static com.ricardorlg.vetclinic.matchers.ApiErrorMatcher.hasErrorWithCodeAndField;
import static net.serenitybdd.screenplay.EventualConsequence.eventually;
import static net.serenitybdd.screenplay.GivenWhenThen.seeThat;
import static net.serenitybdd.screenplay.actors.OnStage.theActorInTheSpotlight;
import static org.hamcrest.CoreMatchers.containsStringIgnoringCase;
import static org.hamcrest.CoreMatchers.equalTo;

@SuppressWarnings("unused")
public class CommonStepDefinitions {

    @Given("{actor} is in the {word} {word} owner information page")
    public void isInTheOwnerInformationPage(Actor actor, String firstName, String lastName) {
        actor.attemptsTo(
                Navigates.toTheOwnerPage(firstName + " " + lastName),
                RememberThat.theValueOf(Constants.FETCHED_OWNER_ID).isAnsweredBy(theIdOfTheOwner(firstName, lastName))
        );
    }

    @And("{actor} has registered the following owner(s) in the system")
    public void theFollowingOwnersAreRegisteredInTheSystem(Actor actor, List<OwnerPersonalInformation> ownersList) {
        actor.has(RegisteredOwners.from(ownersList));
    }

    @Then("{pronoun} should see an alert containing the following error")
    public void shouldSeeAnAlertContainingTheFollowingError(Actor actor, DocString expectedErrorContent) {
        Serenity.recordReportData()
                .withTitle("Expected alert message")
                .andContents(expectedErrorContent.getContent());

        actor.should(
                eventually(
                        seeThat(theDisplayedAlertErrorContent(), containsStringIgnoringCase(expectedErrorContent.getContent()))
                                .because("Then %s should contain all the invalid fields")
                ).waitingForNoLongerThan(20)
                        .seconds()
        );
    }

    @Then("the system should return a {int} response code")
    public void theSystemShouldReturnAResponseCode(int expectedResponseCode) {
        theActorInTheSpotlight().should(
                eventually(seeThat(TheResponse.statusCode(), equalTo(expectedResponseCode)))
                        .waitingForNoLongerThan(10)
                        .seconds()
        );
    }


    @Then("the response body should include the error with code {word} and field {word}")
    public void theResponseBodyShouldIncludeTheError(String codeError, String fieldError) {
        theActorInTheSpotlight()
                .should(
                        seeThat(theErrorsInResponse(), hasErrorWithCodeAndField(codeError, fieldError))
                );
    }
}
