package com.ricardorlg.vetclinc.questions;

import com.ricardorlg.vetclinc.models.common.VeterinarianInformation;
import com.ricardorlg.vetclinc.models.common.VisitInformation;
import com.ricardorlg.vetclinc.ui.AddVisitPage;
import com.ricardorlg.vetclinc.ui.VeterinariansPage;
import net.serenitybdd.screenplay.Question;
import net.thucydides.core.pages.components.HtmlTable;
import org.openqa.selenium.By;

import java.util.List;

public final class VetClinicQuestions {

    public static Question<List<VeterinarianInformation>> theDisplayedVeterinarians() {
        return Question.about("the displayed veterinarians")
                .answeredBy(actor -> {
                    var table = HtmlTable.inTable(VeterinariansPage.DISPLAYED_VETERINARIANS_TABLE.resolveFor(actor));
                    return table.getRows()
                            .stream()
                            .map(row -> new VeterinarianInformation(
                                    row.get("Name"),
                                    row.getOrDefault("Specialties", "")
                            ))
                            .toList();
                });
    }

    public static Question<List<VisitInformation>> theDisplayedPreviousVisitsOf(String petName) {
        return Question.about("the displayed previous visits of " + petName)
                .answeredBy(actor -> {
                    var previousVisitsElements = AddVisitPage.PREVIOUS_VISIT_ROW.resolveAllFor(actor);
                    return previousVisitsElements
                            .stream()
                            .map(element -> {
                                var dateElement = element.findElement(By.xpath(".//td[1]"));
                                var descriptionElement = element.findElement(By.xpath(".//td[2]"));
                                return new VisitInformation(
                                        dateElement.getText().trim(),
                                        descriptionElement.getText().trim()
                                );
                            })
                            .toList();
                });
    }
}
