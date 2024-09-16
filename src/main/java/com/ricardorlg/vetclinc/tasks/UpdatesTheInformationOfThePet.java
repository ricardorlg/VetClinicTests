package com.ricardorlg.vetclinc.tasks;

import com.ricardorlg.vetclinc.models.common.PetInformation;
import com.ricardorlg.vetclinc.ui.OwnerPage;
import com.ricardorlg.vetclinc.ui.PetPage;
import com.ricardorlg.vetclinc.utils.Constants;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.RememberThat;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.Tasks;
import net.serenitybdd.screenplay.actions.Click;
import net.serenitybdd.screenplay.actions.Enter;
import net.serenitybdd.screenplay.actions.Scroll;
import net.serenitybdd.screenplay.actions.SelectFromOptions;
import net.serenitybdd.screenplay.waits.WaitUntil;

import static com.ricardorlg.vetclinc.ui.PetPage.PET_TYPE_SELECT;
import static com.ricardorlg.vetclinc.ui.PetPage.SUBMIT_BUTTON;
import static net.serenitybdd.screenplay.matchers.WebElementStateMatchers.hasValue;

public class UpdatesTheInformationOfThePet implements Task{
    private final PetInformation petToUpdate;
    private final PetInformation newPetInformation;

    UpdatesTheInformationOfThePet(PetInformation petToUpdate, PetInformation newPetInformation) {
        this.petToUpdate = petToUpdate;
        this.newPetInformation = newPetInformation;
    }

    public static UpdatesTheInformationOfThePetBuilder petToUpdate(PetInformation petToUpdate) {
        return new UpdatesTheInformationOfThePetBuilder().withPetToUpdate(petToUpdate);
    }

    @Override
    public <T extends Actor> void performAs(T actor) {
        String petsOwner = actor.recall(Constants.LATEST_REGISTERED_PET_OWNER_NAME);
        if(petsOwner==null || petsOwner.isBlank()) {
            throw new IllegalStateException("The owner of the pet to update has not been registered");
        }
        actor.wasAbleTo(
                Navigates.toTheOwnerPage(petsOwner)
        );
        actor.attemptsTo(
                Scroll.to(OwnerPage.PET_WITH_NAME_LINK.of(petToUpdate.name())),
                Click.on(OwnerPage.PET_WITH_NAME_LINK.of(petToUpdate.name())),
                WaitUntil.the(PetPage.PET_NAME_FIELD, hasValue(petToUpdate.name()))
                        .forNoMoreThan(10)
                        .seconds(),
                Enter.theValue(newPetInformation.name()).into(PetPage.PET_NAME_FIELD),
                Enter.theValue(newPetInformation.birthDate()).into(PetPage.PET_BIRTH_DATE_FIELD),
                SelectFromOptions.byVisibleText(newPetInformation.type()).from(PET_TYPE_SELECT),
                Click.on(SUBMIT_BUTTON),
                RememberThat.theValueOf(Constants.UPDATED_PET_INFORMATION).is(newPetInformation.withParsedBirthDate("MM-dd-yyyy")),
                RememberThat.theValueOf(Constants.OLD_PET_INFORMATION).is(petToUpdate)
        );

    }

    public static class UpdatesTheInformationOfThePetBuilder {
        private PetInformation petToUpdate;

        UpdatesTheInformationOfThePetBuilder withPetToUpdate(PetInformation petToUpdate) {
            this.petToUpdate = petToUpdate;
            return this;
        }

        public UpdatesTheInformationOfThePet with(PetInformation newPetInformation) {
          return Tasks.instrumented(UpdatesTheInformationOfThePet.class, petToUpdate, newPetInformation);
        }
    }
}
