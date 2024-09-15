package com.ricardorlg.vetclinc.tasks;

import net.serenitybdd.screenplay.Performable;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.actions.Open;

public final class Navigate {

    public static Performable toTheOwnerRegistrationPage() {
        return Task.called("{0} navigates to the owner registration page")
                .whereTheActorAttemptsTo(
                        Open.browserOn().thePageNamed("home.page")
                );
    }
}
