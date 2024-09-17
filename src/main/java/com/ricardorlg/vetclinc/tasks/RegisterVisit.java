package com.ricardorlg.vetclinc.tasks;

import com.ricardorlg.vetclinc.models.common.VisitInformation;
import com.ricardorlg.vetclinc.ui.AddVisitPage;
import com.ricardorlg.vetclinc.ui.OwnerPage;
import com.ricardorlg.vetclinc.utils.Constants;
import net.serenitybdd.annotations.Step;
import net.serenitybdd.markers.CanBeSilent;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.RememberThat;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.Tasks;
import net.serenitybdd.screenplay.actions.Click;
import net.serenitybdd.screenplay.actions.Enter;
import net.serenitybdd.screenplay.waits.WaitUntil;

import static net.serenitybdd.screenplay.matchers.WebElementStateMatchers.isVisible;

public class RegisterVisit implements Task, CanBeSilent {

    private final String petName;
    private final VisitInformation visitInformation;
    private boolean withNoReporting = false;

    RegisterVisit(String petName, VisitInformation visitInformation) {
        this.petName = petName;
        this.visitInformation = visitInformation;
    }

    public static RegisterVisitBuilder forPet(String petName) {
        return new RegisterVisitBuilder(petName);
    }

    @Override
    @Step("{0} registers a new visit for the pet #petName")
    public <T extends Actor> void performAs(T actor) {
        String ownerName = actor.recall(Constants.LATEST_REGISTERED_PET_OWNER_NAME);
        actor.attemptsTo(
                Navigates.toTheOwnerPage(ownerName),
                WaitUntil.the(OwnerPage.PET_WITH_NAME_LINK.of(petName), isVisible())
                        .forNoMoreThan(10)
                        .seconds(),
                Click.on(OwnerPage.ADD_VISIT_FOR_PET_BUTTON.of(petName).called("the Add Visit button for " + petName)),
                WaitUntil.the(AddVisitPage.VISIT_DATE_FIELD, isVisible())
                        .forNoMoreThan(10)
                        .seconds(),
                Enter.theValue(visitInformation.visitDate()).into(AddVisitPage.VISIT_DATE_FIELD),
                Enter.theValue(visitInformation.description()).into(AddVisitPage.DESCRIPTION_FIELD),
                Click.on(AddVisitPage.ADD_NEW_VISIT_BUTTON),
                RememberThat.theValueOf(Constants.LATEST_REGISTERED_VISIT_INFORMATION).is(visitInformation.withParsedVisitDate("MM-dd-yyyy"))
        );
    }

    @Override
    public boolean isSilent() {
        return withNoReporting;
    }

    public RegisterVisit withNoReporting() {
        this.withNoReporting = true;
        return this;
    }

    public static class RegisterVisitBuilder {
        private final String petName;

        RegisterVisitBuilder(String petName) {
            this.petName = petName;
        }


        public RegisterVisit withInformation(VisitInformation visitInformation) {
            return Tasks.instrumented(RegisterVisit.class, petName, visitInformation);
        }
    }

}
