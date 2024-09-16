package com.ricardorlg.vetclinc.questions;

import com.ricardorlg.vetclinc.models.api.owners.CompleteOwnerInformation;
import com.ricardorlg.vetclinc.models.common.PetInformation;
import com.ricardorlg.vetclinc.models.common.VisitInformation;
import com.ricardorlg.vetclinc.models.web.OwnerPageCompleteInformation;
import com.ricardorlg.vetclinc.models.web.OwnerRowInformation;
import com.ricardorlg.vetclinc.models.web.PetsAndVisitsInformation;
import com.ricardorlg.vetclinc.ui.AllOwnersPage;
import com.ricardorlg.vetclinc.ui.OwnerPage;
import com.ricardorlg.vetclinc.utils.EndPoints;
import net.serenitybdd.screenplay.Question;
import net.serenitybdd.screenplay.questions.Text;
import net.serenitybdd.screenplay.rest.interactions.Get;
import net.serenitybdd.screenplay.rest.questions.TheResponse;
import net.thucydides.core.pages.components.HtmlTable;
import org.openqa.selenium.By;

import java.util.ArrayList;
import java.util.List;

import static com.ricardorlg.vetclinc.questions.CommonApiQuestions.theOwnerInResponse;
import static com.ricardorlg.vetclinc.questions.CommonApiQuestions.theOwnersInResponse;
import static com.ricardorlg.vetclinc.questions.CommonWebQuestions.theElementsOf;
import static com.ricardorlg.vetclinc.utils.Constants.COMMON_DATE_FORMAT;
import static com.ricardorlg.vetclinc.utils.Constants.OWNER_PAGE_DATE_FORMAT;
import static com.ricardorlg.vetclinc.utils.Utils.parseDate;
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
                    return actor.asksFor(theOwnersInResponse());
                });
    }

    public static Question<OwnerPageCompleteInformation> theDisplayedOwnerInformation() {
        return Question.about("the displayed owner personal information")
                .answeredBy(actor -> {
                    var fullName = actor.asksFor(Text.of(OwnerPage.NAME_FIELD));
                    var address = actor.asksFor(Text.of(OwnerPage.ADDRESS_FIELD));
                    var city = actor.asksFor(Text.of(OwnerPage.CITY_FIELD));
                    var telephone = actor.asksFor(Text.of(OwnerPage.TELEPHONE_FIELD));
                    var petsAndVisits = actor.asksFor(theDisplayedPetsAndVisitsInformation());
                    return new OwnerPageCompleteInformation(fullName, address, city, telephone, petsAndVisits);
                });
    }

    public static Question<List<PetsAndVisitsInformation>> theDisplayedPetsAndVisitsInformation() {
        return Question.about("the displayed pets and visits information")
                .answeredBy(actor -> {
                    var rows = actor.asksFor(theElementsOf(OwnerPage.PETS_AND_VISITS_DATA));
                    if (rows.isEmpty()) {
                        return new ArrayList<>();
                    } else {
                        return rows
                                .stream()
                                .map(row -> {
                                    //each row is a pet, so first get the pet information
                                    var petNameElement = row.findElement(By.xpath(".//dt[text()='Name']/following-sibling::dd"));
                                    var petBirthDateElement = row.findElement(By.xpath(".//dt[text()='Birth Date']/following-sibling::dd"));
                                    var petTypeElement = row.findElement(By.xpath(".//dt[text()='Type']/following-sibling::dd"));
                                    var petName = petNameElement.getText();
                                    var petBirthDate = parseDate(petBirthDateElement.getText(), OWNER_PAGE_DATE_FORMAT, COMMON_DATE_FORMAT);
                                    var petType = petTypeElement.getText();
                                    var pet = new PetInformation(petName, petBirthDate, petType);
                                    //then get the visits information
                                    var visitsTable = row.findElement(By.xpath(".//table"));
                                    var htmlTable = new HtmlTable(visitsTable);
                                    var innerTableRows = htmlTable.getRows();
                                    var visits = innerTableRows
                                            .stream()
                                            .limit(innerTableRows.size() - 1)
                                            .map(visitRow -> {
                                                var visitDate = parseDate(visitRow.get("Visit Date"), OWNER_PAGE_DATE_FORMAT, COMMON_DATE_FORMAT);
                                                var visitDescription = visitRow.get("Description");
                                                return new VisitInformation(visitDate, visitDescription);
                                            })
                                            .toList();
                                    return new PetsAndVisitsInformation(pet, visits);
                                }).toList();
                    }
                });
    }

    public static Question<CompleteOwnerInformation> theDetailsOfTheOwnerWithId(int ownerId) {
        return Question.about("the details of the owner with id " + ownerId)
                .answeredBy(actor -> {
                    actor.attemptsTo(
                            Get.resource(EndPoints.GET_OWNER_BY_ID_PATH.getPath())
                                    .with(request -> request.pathParam("ownerId", ownerId))
                                    .withNoReporting()
                    );

                    actor.should(
                            eventually(seeThat(TheResponse.statusCode(), equalTo(200)))
                                    .waitingForNoLongerThan(10)
                                    .seconds()
                                    .withNoReporting()
                    );
                    return actor.asksFor(theOwnerInResponse());
                });
    }
}
