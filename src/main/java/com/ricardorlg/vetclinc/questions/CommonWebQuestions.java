package com.ricardorlg.vetclinc.questions;

import net.serenitybdd.screenplay.Question;
import net.serenitybdd.screenplay.actions.AlertText;

import java.util.stream.Collectors;

public final class CommonWebQuestions {

    public static Question<String> theDisplayedAlertErrorContent() {
        return Question.about("the content of visible alert")
                .answeredBy(actor -> {
                    var unparsed = actor.asksFor(AlertText.currentlyVisible());
                    return unparsed
                            .lines()
                            .map(String::trim)
                            .filter(s -> !s.isBlank())
                            .sorted()
                            .collect(Collectors.joining("\n"));
                });
    }
}
