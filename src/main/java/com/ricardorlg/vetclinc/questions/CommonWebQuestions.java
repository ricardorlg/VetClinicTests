package com.ricardorlg.vetclinc.questions;

import com.ricardorlg.vetclinc.utils.Constants;
import net.serenitybdd.core.pages.WebElementFacade;
import net.serenitybdd.screenplay.Question;
import net.serenitybdd.screenplay.actions.AlertText;
import net.serenitybdd.screenplay.targets.Target;
import org.openqa.selenium.NoAlertPresentException;

import java.util.List;
import java.util.stream.Collectors;

public final class CommonWebQuestions {

    public static Question<String> theDisplayedAlertErrorContent() {
        return Question.about("the content of visible alert")
                .answeredBy(actor -> {
                    try {
                        var unparsed = actor.asksFor(AlertText.currentlyVisible());
                        return unparsed
                                .lines()
                                .map(String::trim)
                                .filter(s -> !s.isBlank())
                                .sorted()
                                .collect(Collectors.joining("\n"));
                    } catch (NoAlertPresentException e) {
                        throw new AssertionError("There is no alert present", e);
                    }
                });
    }

    public static Question<List<WebElementFacade>> theElementsOf(Target target) {
        return Question.about("the number of  " + target.getName())
                .answeredBy(target::resolveAllFor);
    }

    public static Question<Boolean> theCurrentPageIs(String pageName) {
        return Question.about("the current page is " + pageName)
                .answeredBy(actor -> {
                    String currentPageTitle = actor.recall(Constants.CURRENT_PAGE_TITLE);
                    return pageName.equalsIgnoreCase(currentPageTitle);
                });
    }
}
