package com.ricardorlg.vetclinc.tasks;

import com.ricardorlg.vetclinc.ui.AllOwnersPage;
import net.serenitybdd.annotations.Step;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Interaction;
import net.serenitybdd.screenplay.Tasks;
import net.serenitybdd.screenplay.actions.Enter;
import org.openqa.selenium.Keys;

public class FilterOwnersTable implements Interaction {
    private final String filter;

    public FilterOwnersTable(String filter) {
        this.filter = filter;
    }

    public static Interaction with(String filter){
        return Tasks.instrumented(FilterOwnersTable.class, filter);
    }

    @Override
    @Step("{0} filters the owners table with the search filter: #filter")
    public <T extends Actor> void performAs(T actor) {
        actor.attemptsTo(
                Enter.theValue(filter).into(AllOwnersPage.SEARCH_FILTER).thenHit(Keys.ENTER)
        );
    }
}
