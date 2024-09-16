package com.ricardorlg.vetclinc.tasks;

import net.serenitybdd.annotations.Step;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.RememberThat;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.waits.Wait;

import static com.ricardorlg.vetclinc.questions.OwnerQuestions.theDisplayedOwnersTable;
import static com.ricardorlg.vetclinc.utils.Constants.OWNER_TO_SELECT;
import static org.hamcrest.Matchers.*;
import static org.hamcrest.collection.IsEmptyCollection.empty;

public class SearchOwnerInOwnersTable implements Task {
    private final String ownerFirstName;
    private final String ownerLastName;

    SearchOwnerInOwnersTable(String ownerFirstName, String ownerLastName) {
        this.ownerFirstName = ownerFirstName;
        this.ownerLastName = ownerLastName;
    }

    public static SearchOwnerInOwnersTable withName(String ownerFirstName, String ownerLastName) {
        return new SearchOwnerInOwnersTable(ownerFirstName, ownerLastName);
    }

    @Override
    @Step("{0} searches for the owner with first name '#ownerFirstName' and last name '#ownerLastName'")
    public <T extends Actor> void performAs(T actor) {
        actor.attemptsTo(
                Navigate.toTheAllOwnersPage(),
                Wait.until(theDisplayedOwnersTable(), hasSize(greaterThan(2)))
                        .forNoMoreThan(20)
                        .seconds(),
                FilterOwnersTable.with(ownerFirstName),
                RememberThat.theValueOf(OWNER_TO_SELECT).is(ownerFirstName + " " + ownerLastName)
        );
    }
}
