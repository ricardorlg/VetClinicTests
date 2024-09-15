package com.ricardorlg.vetclinc.questions;

import com.ricardorlg.vetclinc.models.web.OwnerRowInformation;
import com.ricardorlg.vetclinc.ui.AllOwnersPage;
import net.serenitybdd.screenplay.Question;
import net.thucydides.core.pages.components.HtmlTable;

import java.util.List;

public final class OwnerQuestions {


    public static Question<List<OwnerRowInformation>> theDisplayedOwnersTable() {
        return  Question.about("the displayed owners information table")
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
}
