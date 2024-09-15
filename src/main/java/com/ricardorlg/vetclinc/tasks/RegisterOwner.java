package com.ricardorlg.vetclinc.tasks;

import com.ricardorlg.vetclinc.models.common.OwnerPersonalInformation;
import com.ricardorlg.vetclinc.utils.Constants;
import net.serenitybdd.markers.CanBeSilent;
import net.serenitybdd.markers.IsHidden;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Performable;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.Tasks;
import net.serenitybdd.screenplay.actions.Click;
import net.serenitybdd.screenplay.actions.Enter;
import net.serenitybdd.screenplay.conditions.Check;
import net.serenitybdd.screenplay.questions.TheMemory;
import net.serenitybdd.screenplay.rest.interactions.Post;

import static com.ricardorlg.vetclinc.ui.RegisterNewOwnerPage.*;
import static com.ricardorlg.vetclinc.utils.EndPoints.REGISTER_OWNER_PATH;

public class RegisterOwner implements Task, IsHidden, CanBeSilent {
    private final OwnerPersonalInformation ownerInformation;
    private final boolean withNoReporting;

    public RegisterOwner(OwnerPersonalInformation ownerInformation,boolean withNoReporting) {
        this.ownerInformation = ownerInformation;
        this.withNoReporting = withNoReporting;
    }

    public static RegisterOwner withInformation(OwnerPersonalInformation ownerInformation) {
        return Tasks.instrumented(RegisterOwner.class, ownerInformation,false);
    }

    public RegisterOwner withNoReporting() {
        return Tasks.instrumented(RegisterOwner.class, ownerInformation,true);
    }

    @Override
    public <T extends Actor> void performAs(T actor) {
        actor.attemptsTo(
                Check.whether(TheMemory.withKey(Constants.USE_WEB_FORM_KEY).isPresent())
                        .andIfSo(
                                registerOwnerUsingWebForm()
                        ).otherwise(
                                registerOwnerUsingApi()
                        )
        );
    }

    private Performable registerOwnerUsingWebForm() {
        return Task.where("{0} registers a new Owner using the web form",
                Enter.theValue(ownerInformation.firstName()).into(FIRST_NAME_FIELD),
                Enter.theValue(ownerInformation.lastName()).into(LAST_NAME_FIELD),
                Enter.theValue(ownerInformation.address()).into(ADDRESS_FIELD),
                Enter.theValue(ownerInformation.city()).into(CITY_FIELD),
                Enter.theValue(ownerInformation.telephone()).into(PHONE_FIELD),
                Click.on(SUBMIT_BUTTON)
        );
    }

    private Performable registerOwnerUsingApi() {
        return Task.where("{0} registers a new Owner using the API",
                Post.to(REGISTER_OWNER_PATH.getPath())
                        .with(request -> {
                                    request.header("Content-Type", "application/json");
                                    request.body(ownerInformation);
                                    return request;
                                }
                        )
        );
    }

    @Override
    public boolean isSilent() {
        return withNoReporting;
    }
}
