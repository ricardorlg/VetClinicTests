package com.ricardorlg.vetclinic.stepdefinitions;

import io.cucumber.docstring.DocString;
import io.cucumber.java.en.Then;
import net.serenitybdd.core.Serenity;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.EventualConsequence;
import net.serenitybdd.screenplay.GivenWhenThen;
import org.hamcrest.CoreMatchers;

import static com.ricardorlg.vetclinc.questions.CommonWebQuestions.theDisplayedAlertErrorContent;
import static net.serenitybdd.screenplay.EventualConsequence.eventually;
import static net.serenitybdd.screenplay.GivenWhenThen.seeThat;
import static org.hamcrest.CoreMatchers.containsStringIgnoringCase;

public class CommonStepDefinitions {

    @Then("{pronoun} should see an alert containing the following error")
    public void shouldSeeAnAlertContainingTheFollowingError(Actor actor, DocString expectedErrorContent) {
        Serenity.recordReportData()
                .withTitle("Expected alert message")
                .andContents(expectedErrorContent.getContent());

        actor.should(
                eventually(
                        seeThat(theDisplayedAlertErrorContent(), containsStringIgnoringCase(expectedErrorContent.getContent()))
                                .because("Then %s should contain all the invalid fields")
                ).waitingForNoLongerThan(10)
                        .seconds()
        );
    }
}
