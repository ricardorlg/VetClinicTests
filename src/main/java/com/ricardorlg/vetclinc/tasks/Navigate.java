package com.ricardorlg.vetclinc.tasks;

import com.ricardorlg.vetclinc.ui.AllOwnersPage;
import com.ricardorlg.vetclinc.ui.RegisterNewOwnerPage;
import com.ricardorlg.vetclinc.ui.WelcomePage;
import com.ricardorlg.vetclinc.utils.Constants;
import net.serenitybdd.screenplay.Performable;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.actions.Click;
import net.serenitybdd.screenplay.actions.Open;
import net.serenitybdd.screenplay.waits.WaitUntil;

import static net.serenitybdd.screenplay.matchers.WebElementStateMatchers.isCurrentlyVisible;
import static net.serenitybdd.screenplay.matchers.WebElementStateMatchers.isPresent;

public final class Navigate {

    public static Performable navigatesToTheWelcomePage() {
        return Task.where("{0} navigates to the welcome page",
                actor -> {
                    String baseUrl = actor.recall(Constants.BASE_URL_KEY);
                    actor.attemptsTo(
                            Open.url(baseUrl),
                            WaitUntil.the(WelcomePage.WELCOME_MESSAGE, isCurrentlyVisible())
                                    .forNoMoreThan(30)
                                    .seconds()
                    );
                }
        );
    }

    public static Performable toTheOwnerRegistrationPage() {
        return Task.called("{0} navigates to the owner registration page")
                .whereTheActorAttemptsTo(
                        navigatesToTheWelcomePage(),
                        Click.on(WelcomePage.OWNERS_MENU),
                        WaitUntil.the(WelcomePage.REGISTER_OWNER_BUTTON, isPresent())
                                .forNoMoreThan(10)
                                .seconds(),
                        Click.on(WelcomePage.REGISTER_OWNER_BUTTON),
                        WaitUntil.the(RegisterNewOwnerPage.FIRST_NAME_FIELD, isCurrentlyVisible())
                                .forNoMoreThan(20)
                                .seconds()
                );
    }

    public static Performable toTheAllOwnersPage() {
        return Task.called("{0} navigates to the all owners page")
                .whereTheActorAttemptsTo(
                        navigatesToTheWelcomePage(),
                        Click.on(WelcomePage.OWNERS_MENU),
                        WaitUntil.the(WelcomePage.ALL_OWNERS_BUTTON, isPresent())
                                .forNoMoreThan(10)
                                .seconds(),
                        Click.on(WelcomePage.ALL_OWNERS_BUTTON),
                        WaitUntil.the(AllOwnersPage.OWNERS_TABLE, isCurrentlyVisible())
                                .forNoMoreThan(20)
                                .seconds()
                );
    }
}
