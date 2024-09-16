package com.ricardorlg.vetclinc.tasks;

import com.ricardorlg.vetclinc.models.common.OwnerPersonalInformation;
import com.ricardorlg.vetclinc.questions.OwnerQuestions;
import com.ricardorlg.vetclinc.ui.EditOwnerPage;
import com.ricardorlg.vetclinc.ui.OwnerPage;
import com.ricardorlg.vetclinc.utils.Constants;
import net.serenitybdd.annotations.Step;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.RememberThat;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.actions.Click;
import net.serenitybdd.screenplay.actions.Enter;
import net.serenitybdd.screenplay.waits.WaitUntil;

import static net.serenitybdd.screenplay.matchers.WebElementStateMatchers.hasValue;
import static net.serenitybdd.screenplay.matchers.WebElementStateMatchers.isVisible;

public class UpdateOwnerPersonalInformation implements Task {
    private final String ownerFirstName;
    private final String ownerLastName;
    private final OwnerPersonalInformation newOwnerPersonalInformation;

    UpdateOwnerPersonalInformation(String ownerFirstName, String ownerLastName, OwnerPersonalInformation newOwnerPersonalInformation) {
        this.ownerFirstName = ownerFirstName;
        this.ownerLastName = ownerLastName;
        this.newOwnerPersonalInformation = newOwnerPersonalInformation;
    }

    public static UpdateOwnerPersonalInformation with(String ownerFirstName, String ownerLastName, OwnerPersonalInformation newOwnerPersonalInformation) {
        return new UpdateOwnerPersonalInformation(ownerFirstName, ownerLastName, newOwnerPersonalInformation);
    }

    @Override
    @Step("{0} updates the personal information of the owner #ownerFirstName #ownerLastName")
    public <T extends Actor> void performAs(T actor) {
        var ownerId = actor.asksFor(OwnerQuestions.theRegisteredOwnersInTheSystem())
                .stream()
                .filter(owner -> owner.firstName().equals(ownerFirstName) && owner.lastName().equals(ownerLastName))
                .findFirst()
                .orElseThrow(() -> new IllegalStateException("the owner " + ownerFirstName + " " + ownerLastName + " is not registered"))
                .id();
        actor.attemptsTo(
                SearchOwnerInOwnersTable.withName(ownerFirstName, ownerLastName),
                SelectFromOwnersTable.theOwnerWithFullName(ownerFirstName + " " + ownerLastName),
                WaitUntil.the(OwnerPage.EDIT_OWNER_BUTTON, isVisible()).forNoMoreThan(10).seconds(),
                Click.on(OwnerPage.EDIT_OWNER_BUTTON),
                WaitUntil.the(EditOwnerPage.FIRST_NAME_FIELD, hasValue(ownerFirstName)).forNoMoreThan(10).seconds(),
                Enter.theValue(newOwnerPersonalInformation.firstName()).into(EditOwnerPage.FIRST_NAME_FIELD),
                Enter.theValue(newOwnerPersonalInformation.lastName()).into(EditOwnerPage.LAST_NAME_FIELD),
                Enter.theValue(newOwnerPersonalInformation.address()).into(EditOwnerPage.ADDRESS_FIELD),
                Enter.theValue(newOwnerPersonalInformation.city()).into(EditOwnerPage.CITY_FIELD),
                Enter.theValue(newOwnerPersonalInformation.telephone()).into(EditOwnerPage.TELEPHONE_FIELD),
                Click.on(EditOwnerPage.SUBMIT_BUTTON),
                RememberThat.theValueOf(Constants.FETCHED_OWNER_ID).is(ownerId),
                RememberThat.theValueOf(Constants.REGISTERED_OWNER_INFORMATION).is(newOwnerPersonalInformation)
        );
    }
}
