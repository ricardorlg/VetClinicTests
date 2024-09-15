package com.ricardorlg.vetclinic.stepdefinitions;

import com.ricardorlg.vetclinc.models.common.OwnerPersonalInformation;
import com.ricardorlg.vetclinc.models.web.OwnerRowInformation;
import com.ricardorlg.vetclinc.tasks.FilterOwnersTable;
import com.ricardorlg.vetclinc.tasks.Navigate;
import com.ricardorlg.vetclinc.utils.Constants;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.RememberThat;
import net.serenitybdd.screenplay.waits.Wait;

import java.util.List;

import static com.ricardorlg.vetclinc.questions.OwnerQuestions.theDisplayedOwnersTable;
import static net.serenitybdd.screenplay.EventualConsequence.eventually;
import static net.serenitybdd.screenplay.GivenWhenThen.seeThat;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.collection.IsEmptyCollection.empty;

@SuppressWarnings("unused")
public class ManageOwnersSteps {

    @Given("{pronoun} wants to filter the data of the owners displayed in the Owners page")
    public void wantsToFilterTheDataOfTheOwnersDisplayedInTheOwnersPage(Actor actor) {
        actor.attemptsTo(
                RememberThat.theValueOf(Constants.USE_WEB_FORM_KEY).is(true),
                Navigate.toTheAllOwnersPage(),
                Wait.until(theDisplayedOwnersTable(), is(not(empty())))
                        .forNoMoreThan(20)
                        .seconds()
        );
    }

    @When("{pronoun} filters using the following information {string}")
    public void filtersUsingTheFollowingInformation(Actor actor, String filterInformation) {
        actor.attemptsTo(
                FilterOwnersTable.with(filterInformation)
        );
    }

    @Then("{pronoun} should see the following owners in the table")
    public void shouldSeeTheFollowingOwnersInTheTable(Actor actor, List<OwnerPersonalInformation> expectedOwnersInformation) {
        var expectedOwnersRows = expectedOwnersInformation.stream()
                .map(OwnerPersonalInformation::toOwnerRowInformation)
                .toList();
        var simpleExpectedOwnersInformation = expectedOwnersRows.stream()
                .map(OwnerRowInformation::name)
                .toList();

        actor.should(
                eventually(seeThat(theDisplayedOwnersTable(), hasSize(expectedOwnersRows.size())))
                        .waitingForNoLongerThan(10)
                        .seconds(),
                seeThat(theDisplayedOwnersTable(), containsInAnyOrder(expectedOwnersRows.toArray()))
                        .because("Then %s should only have the owners with the following information: " + simpleExpectedOwnersInformation)
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
}
