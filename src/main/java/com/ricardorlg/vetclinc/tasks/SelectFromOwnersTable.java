package com.ricardorlg.vetclinc.tasks;

import com.ricardorlg.vetclinc.ui.AllOwnersPage;
import net.serenitybdd.markers.IsHidden;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Interaction;
import net.serenitybdd.screenplay.actions.Click;
import net.serenitybdd.screenplay.waits.WaitUntil;

import static net.serenitybdd.screenplay.matchers.WebElementStateMatchers.isCurrentlyVisible;

public class SelectFromOwnersTable implements Interaction, IsHidden {
    private final String ownerToSelect;

    SelectFromOwnersTable(String ownerToSelect) {
        this.ownerToSelect = ownerToSelect;
    }

    public static SelectFromOwnersTable theOwnerWithFullName(String ownerToSelect) {
        return new SelectFromOwnersTable(ownerToSelect);
    }

    @Override
    public <T extends Actor> void performAs(T actor) {
        var rowToClick = AllOwnersPage.ROW_WITH_OWNER_NAME.of(ownerToSelect);
        actor.attemptsTo(
                WaitUntil.the(rowToClick, isCurrentlyVisible())
                        .forNoMoreThan(10)
                        .seconds(),
                Click.on(rowToClick)
        );
    }
}
