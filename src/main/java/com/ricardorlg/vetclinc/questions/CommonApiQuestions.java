package com.ricardorlg.vetclinc.questions;

import com.ricardorlg.vetclinc.models.api.ErrorResponse;
import com.ricardorlg.vetclinc.models.api.ErrorsItem;
import net.serenitybdd.screenplay.Question;
import net.serenitybdd.screenplay.rest.questions.LastResponse;

import java.util.List;

public final class CommonApiQuestions {

    public static Question<List<ErrorsItem>> theErrorsInResponse() {
        return Question.about("the errors in the latest api response")
                .answeredBy(actor -> {
                    var response = actor.asksFor(LastResponse.received()).as(ErrorResponse.class);
                    return response.errors();
                });
    }

}
