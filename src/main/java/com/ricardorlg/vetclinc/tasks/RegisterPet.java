package com.ricardorlg.vetclinc.tasks;

import com.ricardorlg.vetclinc.models.api.pets.CompletePetsInformation;
import com.ricardorlg.vetclinc.models.api.pets.CreatePetRequestBody;
import com.ricardorlg.vetclinc.models.common.PetInformation;
import com.ricardorlg.vetclinc.ui.OwnerPage;
import com.ricardorlg.vetclinc.ui.PetPage;
import com.ricardorlg.vetclinc.utils.Constants;
import com.ricardorlg.vetclinc.utils.EndPoints;
import net.serenitybdd.markers.CanBeSilent;
import net.serenitybdd.markers.IsHidden;
import net.serenitybdd.screenplay.*;
import net.serenitybdd.screenplay.actions.Click;
import net.serenitybdd.screenplay.actions.Enter;
import net.serenitybdd.screenplay.actions.SelectFromOptions;
import net.serenitybdd.screenplay.conditions.Check;
import net.serenitybdd.screenplay.questions.TheMemory;
import net.serenitybdd.screenplay.rest.interactions.Post;

import static com.ricardorlg.vetclinc.questions.CommonApiQuestions.theIdOfTheOwner;
import static com.ricardorlg.vetclinc.questions.CommonApiQuestions.theRegisteredPetTypes;
import static com.ricardorlg.vetclinc.ui.PetPage.*;
import static com.ricardorlg.vetclinc.utils.Constants.LATEST_REGISTERED_PET;

public class RegisterPet implements Task, IsHidden, CanBeSilent {

    private final String ownerFirstName;
    private final String ownerLastName;
    private final PetInformation petInformation;
    private boolean withNoReporting = false;

    RegisterPet(String ownerFirstName, String ownerLastName, PetInformation petInformation) {
        this.ownerFirstName = ownerFirstName;
        this.ownerLastName = ownerLastName;
        this.petInformation = petInformation;
    }

    public static RegisterPetBuilder forOwner(String ownerFirstName, String ownerLastName) {
        return new RegisterPetBuilder().withOwnerFirstName(ownerFirstName).withOwnerLastName(ownerLastName);
    }

    public RegisterPet withNoReporting() {
        this.withNoReporting = true;
        return this;
    }

    @Override
    public <T extends Actor> void performAs(T actor) {
        actor.attemptsTo(
                Check.whether(TheMemory.withKey(Constants.USE_WEB_FORM_KEY).isPresent())
                        .andIfSo(
                                registerPetUsingWebForm()
                        ).otherwise(
                                registerPetUsingApi()
                        )
        );
    }

    private Performable registerPetUsingWebForm() {
        return Task.where("{0} registers a new Pet for " + ownerFirstName + " " + ownerLastName + " using the web form",
                Navigates.toTheAddNewPetPageOf(ownerFirstName, ownerLastName),
                RememberThat.theValueOf(Constants.CURRENT_PAGE_TITLE).is(PetPage.PAGE_TITLE),
                Enter.theValue(petInformation.name()).into(PET_NAME_FIELD),
                Enter.theValue(petInformation.birthDate()).into(PET_BIRTH_DATE_FIELD),
                SelectFromOptions.byVisibleText(petInformation.type()).from(PET_TYPE_SELECT),
                Click.on(SUBMIT_BUTTON),
                RememberThat.theValueOf(LATEST_REGISTERED_PET).isAnsweredBy(_actor -> petInformation.withParsedBirthDate("MM-dd-yyyy")),
                RememberThat.theValueOf(Constants.CURRENT_PAGE_TITLE).is(OwnerPage.PAGE_TITLE)
        );
    }

    private Performable registerPetUsingApi() {
        return Task.where("{0} registers a new Pet for " + ownerFirstName + " " + ownerLastName + " using the API",
                actor -> {
                    var ownerId = actor.asksFor(theIdOfTheOwner(ownerFirstName, ownerLastName));
                    var dogTypes = actor.asksFor(theRegisteredPetTypes());
                    var petType = dogTypes.stream()
                            .filter(type -> type.name().equalsIgnoreCase(petInformation.type()))
                            .findFirst()
                            .orElse(new CompletePetsInformation.Type(100, petInformation.type()));

                    var request = new CreatePetRequestBody(
                            petInformation.name(),
                            petInformation.birthDate(),
                            String.valueOf(petType.id())
                    );
                    var taskToPerform = Post.to(EndPoints.REGISTER_PET.getPath())
                            .with(requestSpecification -> {
                                requestSpecification.pathParam("ownerId", ownerId);
                                requestSpecification.header("Content-Type", "application/json");
                                requestSpecification.body(request);
                                return requestSpecification;
                            });
                    actor.attemptsTo(
                            Check.whether(withNoReporting)
                                    .andIfSo(
                                            taskToPerform.withNoReporting()
                                    ).otherwise(
                                            taskToPerform
                                    )
                    );
                    actor.remember(Constants.FETCHED_OWNER_ID, ownerId);
                    actor.remember(LATEST_REGISTERED_PET, petInformation);
                    actor.remember(Constants.FULL_PET_INFORMATION_REGISTERED_WITH_API, petInformation.toCompletePetInformation(petType));
                }
        );
    }

    @Override
    public boolean isSilent() {
        return withNoReporting;
    }


    public static class RegisterPetBuilder {
        private String ownerFirstName;
        private String ownerLastName;

        RegisterPetBuilder() {
        }

        public RegisterPetBuilder withOwnerFirstName(String ownerFirstName) {
            this.ownerFirstName = ownerFirstName;
            return this;
        }

        public RegisterPetBuilder withOwnerLastName(String ownerLastName) {
            this.ownerLastName = ownerLastName;
            return this;
        }

        public RegisterPet withPetInformation(PetInformation petInformation) {
            return Tasks.instrumented(RegisterPet.class, ownerFirstName, ownerLastName, petInformation);
        }
    }

}
