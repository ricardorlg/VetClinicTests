package com.ricardorlg.vetclinc.tasks;

import com.ricardorlg.vetclinc.ui.AllOwnersPage;
import com.ricardorlg.vetclinc.ui.RegisterNewOwnerPage;
import com.ricardorlg.vetclinc.ui.WelcomePage;
import net.serenitybdd.screenplay.Performable;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.actions.Click;
import net.serenitybdd.screenplay.actions.Open;
import net.serenitybdd.screenplay.matchers.WebElementStateMatchers;
import net.serenitybdd.screenplay.waits.WaitUntil;

public final class Navigate {

    public static Performable toTheOwnerRegistrationPage() {
        return Task.called("{0} navigates to the owner registration page")
                .whereTheActorAttemptsTo(
                        Open.browserOn().thePageNamed("home.page"),
                        WaitUntil.the(WelcomePage.WELCOME_MESSAGE, WebElementStateMatchers.isCurrentlyVisible())
                                .forNoMoreThan(30)
                                .seconds(),
                        Click.on(WelcomePage.OWNERS_MENU),
                        WaitUntil.the(WelcomePage.REGISTER_OWNER_BUTTON, WebElementStateMatchers.isPresent())
                                .forNoMoreThan(10)
                                .seconds(),
                        Click.on(WelcomePage.REGISTER_OWNER_BUTTON),
                        WaitUntil.the(RegisterNewOwnerPage.FIRST_NAME_FIELD, WebElementStateMatchers.isCurrentlyVisible())
                                .forNoMoreThan(20)
                                .seconds()
                );
    }

    public static Performable toTheAllOwnersPage() {
        return Task.called("{0} navigates to the all owners page")
                .whereTheActorAttemptsTo(
                        Open.browserOn().thePageNamed("home.page"),
                        WaitUntil.the(WelcomePage.WELCOME_MESSAGE, WebElementStateMatchers.isCurrentlyVisible())
                                .forNoMoreThan(30)
                                .seconds(),
                        Click.on(WelcomePage.OWNERS_MENU),
                        WaitUntil.the(WelcomePage.ALL_OWNERS_BUTTON, WebElementStateMatchers.isPresent())
                                .forNoMoreThan(10)
                                .seconds(),
                        Click.on(WelcomePage.ALL_OWNERS_BUTTON),
                        WaitUntil.the(AllOwnersPage.OWNERS_TABLE, WebElementStateMatchers.isCurrentlyVisible())
                                .forNoMoreThan(20)
                                .seconds()
                );
    }
}
