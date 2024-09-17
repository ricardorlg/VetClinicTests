package com.ricardorlg.vetclinc.questions;

import com.ricardorlg.vetclinc.models.common.VeterinarianInformation;
import com.ricardorlg.vetclinc.ui.VeterinariansPage;
import net.serenitybdd.screenplay.Question;
import net.thucydides.core.pages.components.HtmlTable;

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
                                    row.getOrDefault("Specialties","")
                            ))
                            .toList();
                });
    }
}
