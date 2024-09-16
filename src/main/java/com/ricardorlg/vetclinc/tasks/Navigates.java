package com.ricardorlg.vetclinc.tasks;

import com.ricardorlg.vetclinc.ui.*;
import com.ricardorlg.vetclinc.utils.Constants;
import net.serenitybdd.screenplay.Performable;
import net.serenitybdd.screenplay.RememberThat;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.actions.Click;
import net.serenitybdd.screenplay.actions.Open;
import net.serenitybdd.screenplay.conditions.Check;
import net.serenitybdd.screenplay.waits.WaitUntil;

import static com.ricardorlg.vetclinc.questions.CommonWebQuestions.theCurrentPageIs;
import static com.ricardorlg.vetclinc.ui.OwnerPage.ADD_NEW_PET_BUTTON;
import static com.ricardorlg.vetclinc.ui.PetPage.PET_NAME_FIELD;
import static com.ricardorlg.vetclinc.utils.Constants.CURRENT_PAGE_TITLE;
import static net.serenitybdd.screenplay.matchers.WebElementStateMatchers.*;

public final class Navigates {

    public static Performable toTheWelcomePage() {
        return Task.where("{0} navigates to the welcome page",
                actor -> {
                    String baseUrl = actor.recall(Constants.BASE_URL_KEY);
                    actor.attemptsTo(
                            Open.url(baseUrl),
                            WaitUntil.the(WelcomePage.WELCOME_MESSAGE, isCurrentlyVisible())
                                    .forNoMoreThan(30)
                                    .seconds(),
                            RememberThat.theValueOf(CURRENT_PAGE_TITLE).is(WelcomePage.PAGE_TITLE)
                    );
                }
        );
    }

    public static Performable toTheOwnerRegistrationPage() {
        return Task.called("{0} navigates to the owner registration page")
                .whereTheActorAttemptsTo(
                        toTheWelcomePage(),
                        Click.on(WelcomePage.OWNERS_MENU),
                        WaitUntil.the(WelcomePage.REGISTER_OWNER_BUTTON, isPresent())
                                .forNoMoreThan(10)
                                .seconds(),
                        Click.on(WelcomePage.REGISTER_OWNER_BUTTON),
                        WaitUntil.the(RegisterNewOwnerPage.FIRST_NAME_FIELD, isCurrentlyVisible())
                                .forNoMoreThan(20)
                                .seconds(),
                        RememberThat.theValueOf(CURRENT_PAGE_TITLE).is(RegisterNewOwnerPage.PAGE_TITLE)
                );
    }

    public static Performable toTheAllOwnersPage() {
        return Task.called("{0} navigates to the all owners page")
                .whereTheActorAttemptsTo(
                        toTheWelcomePage(),
                        Click.on(WelcomePage.OWNERS_MENU),
                        WaitUntil.the(WelcomePage.ALL_OWNERS_BUTTON, isPresent())
                                .forNoMoreThan(10)
                                .seconds(),
                        Click.on(WelcomePage.ALL_OWNERS_BUTTON),
                        WaitUntil.the(AllOwnersPage.OWNERS_TABLE, isCurrentlyVisible())
                                .forNoMoreThan(20)
                                .seconds(),
                        RememberThat.theValueOf(CURRENT_PAGE_TITLE).is(AllOwnersPage.PAGE_TITLE)
                );
    }

    public static Performable toTheAddNewPetPageOf(String ownerFirstName, String ownerLastName) {
        return Task.called("{0} navigates to the add new pet page for " + ownerFirstName + " " + ownerLastName)
                .whereTheActorAttemptsTo(
                        Check.whether(theCurrentPageIs(PetPage.PAGE_TITLE))
                                .otherwise(
                                        toTheOwnerPage(ownerFirstName + " " + ownerLastName),
                                        WaitUntil.the(ADD_NEW_PET_BUTTON, isCurrentlyVisible())
                                                .forNoMoreThan(10)
                                                .seconds(),
                                        RememberThat.theValueOf(CURRENT_PAGE_TITLE).is(OwnerPage.PAGE_TITLE),
                                        Click.on(ADD_NEW_PET_BUTTON),
                                        WaitUntil.the(PET_NAME_FIELD, isCurrentlyVisible())
                                                .forNoMoreThan(10)
                                                .seconds(),
                                        RememberThat.theValueOf(CURRENT_PAGE_TITLE).is(PetPage.PAGE_TITLE)
                                ).andIfSo(
                                        WaitUntil.the(PET_NAME_FIELD, isCurrentlyVisible())
                                                .forNoMoreThan(10)
                                                .seconds()
                                ),
                        WaitUntil.the(PetPage.OWNER_NAME_FIELD.of(ownerFirstName + " " + ownerLastName), isCurrentlyVisible())
                                .forNoMoreThan(10)
                                .seconds()
                );
    }

    public static Performable toTheOwnerPage(String ownerFullName) {
        return Task.called("{0} navigates to " + ownerFullName + " information page")
                .whereTheActorAttemptsTo(
                        Check.whether(theCurrentPageIs(OwnerPage.PAGE_TITLE))
                                .otherwise(
                                        SearchOwnerInOwnersTable.withName(ownerFullName.split(" ")[0], ownerFullName.split(" ")[1]),
                                        SelectFromOwnersTable.theOwnerWithFullName(ownerFullName),
                                        WaitUntil.the(OwnerPage.NAME_FIELD, containsText(ownerFullName))
                                                .forNoMoreThan(10)
                                                .seconds(),
                                        RememberThat.theValueOf(CURRENT_PAGE_TITLE).is(OwnerPage.PAGE_TITLE)
                                ).andIfSo(
                                        WaitUntil.the(OwnerPage.NAME_FIELD, containsText(ownerFullName))
                                                .forNoMoreThan(10)
                                                .seconds()
                                )
                );
    }
}
