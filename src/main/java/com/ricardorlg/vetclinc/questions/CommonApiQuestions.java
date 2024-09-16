package com.ricardorlg.vetclinc.questions;

import com.ricardorlg.vetclinc.models.api.ErrorResponse;
import com.ricardorlg.vetclinc.models.api.ErrorsItem;
import com.ricardorlg.vetclinc.models.api.owners.CompleteOwnerInformation;
import com.ricardorlg.vetclinc.models.api.pets.CompletePetsInformation;
import com.ricardorlg.vetclinc.utils.EndPoints;
import io.restassured.common.mapper.TypeRef;
import net.serenitybdd.screenplay.Question;
import net.serenitybdd.screenplay.rest.interactions.Get;
import net.serenitybdd.screenplay.rest.questions.LastResponse;
import net.serenitybdd.screenplay.rest.questions.TheResponse;

import java.util.List;

import static net.serenitybdd.screenplay.EventualConsequence.eventually;
import static net.serenitybdd.screenplay.GivenWhenThen.seeThat;
import static org.hamcrest.CoreMatchers.equalTo;

public final class CommonApiQuestions {

    public static Question<List<ErrorsItem>> theErrorsInResponse() {
        return Question.about("the errors in the latest api response")
                .answeredBy(actor -> {
                    var response = actor.asksFor(LastResponse.received()).as(ErrorResponse.class);
                    return response.errors();
                });
    }

    public static Question<List<CompleteOwnerInformation>> theOwnersInResponse() {
        return Question.about("the owners in the latest api response")
                .answeredBy(actor -> actor.asksFor(LastResponse.received()).as(new TypeRef<>() {
                }));
    }

    public static Question<CompleteOwnerInformation> theOwnerInResponse() {
        return Question.about("the owner in the latest api response")
                .answeredBy(actor -> actor.asksFor(LastResponse.received()).as(CompleteOwnerInformation.class));
    }

    public static Question<Integer> theIdOfTheOwner(String ownerFirstName, String ownerLastName) {
        return Question.about("the id of the owner")
                .answeredBy(actor -> {
                    var response = actor.asksFor(OwnerQuestions.theRegisteredOwnersInTheSystem());
                    return response.stream()
                            .filter(owner -> owner.firstName().equalsIgnoreCase(ownerFirstName) && owner.lastName().equalsIgnoreCase(ownerLastName))
                            .findFirst()
                            .map(CompleteOwnerInformation::id)
                            .orElseThrow(() -> new IllegalStateException("The owner %s %s was not found in the response".formatted(ownerFirstName, ownerLastName)));
                });
    }

    public static Question<List<CompletePetsInformation.Type>> theRegisteredPetTypes() {
        return Question.about("the registered pet types in the system")
                .answeredBy(actor -> {
                    actor.attemptsTo(
                            Get.resource(EndPoints.PET_TYPES.getPath())
                                    .withNoReporting()
                    );
                    actor.should(
                            eventually(seeThat(TheResponse.statusCode(), equalTo(200)))
                                    .waitingForNoLongerThan(10)
                                    .seconds()
                                    .withNoReporting()
                    );
                    return actor.asksFor(LastResponse.received()).as(new TypeRef<>() {
                    });
                });
    }

}
