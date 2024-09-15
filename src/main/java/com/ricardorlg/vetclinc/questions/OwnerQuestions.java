package com.ricardorlg.vetclinc.questions;

import com.ricardorlg.vetclinc.models.api.owners.CompleteOwnerInformation;
import com.ricardorlg.vetclinc.models.web.OwnerRowInformation;
import com.ricardorlg.vetclinc.ui.AllOwnersPage;
import com.ricardorlg.vetclinc.utils.EndPoints;
import io.restassured.common.mapper.TypeRef;
import net.serenitybdd.screenplay.Question;
import net.serenitybdd.screenplay.rest.interactions.Get;
import net.serenitybdd.screenplay.rest.questions.LastResponse;
import net.serenitybdd.screenplay.rest.questions.TheResponse;
import net.thucydides.core.pages.components.HtmlTable;

import java.util.List;

import static net.serenitybdd.screenplay.EventualConsequence.eventually;
import static net.serenitybdd.screenplay.GivenWhenThen.seeThat;
import static org.hamcrest.CoreMatchers.equalTo;

public final class OwnerQuestions {


    public static Question<List<OwnerRowInformation>> theDisplayedOwnersTable() {
        return Question.about("the displayed owners information table")
                .answeredBy(actor -> {
                    var element = AllOwnersPage.OWNERS_TABLE.resolveFor(actor);
                    HtmlTable table = new HtmlTable(element);
                    return table.getRows()
                            .stream()
                            .map(row -> new OwnerRowInformation(
                                    row.get("Name"),
                                    row.get("Address"),
                                    row.get("City"),
                                    row.get("Telephone"),
                                    row.get("Pets")
                            ))
                            .toList();
                });
    }

    public static Question<List<CompleteOwnerInformation>> theRegisteredOwnersInTheSystem() {
        return Question.about("the registered owners in the system")
                .answeredBy(actor -> {
                    actor.attemptsTo(
                            Get.resource(EndPoints.ALL_OWNERS_PATH.getPath())
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
