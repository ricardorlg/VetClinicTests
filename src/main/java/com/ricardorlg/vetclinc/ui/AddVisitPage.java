package com.ricardorlg.vetclinc.ui;

import net.serenitybdd.screenplay.targets.Target;
import net.serenitybdd.screenplay.ui.Button;
import org.openqa.selenium.By;

import static org.openqa.selenium.By.tagName;
import static org.openqa.selenium.By.xpath;
import static org.openqa.selenium.support.locators.RelativeLocator.with;

public final class AddVisitPage {

    public static final Target VISIT_DATE_FIELD = Target.the("the visit date field").located(By.cssSelector("input[type='date']"));
    public static final Target DESCRIPTION_FIELD = Target.the("the description field").located(with(tagName("textarea")).below(xpath("//label[text()='Description']")));
    public static final Target ADD_NEW_VISIT_BUTTON = Button.withText("Add New Visit");
    public static final Target PREVIOUS_VISIT_ROW = Target.the("a previous visit").located(By.xpath("//h3[text()='Previous Visits']/following-sibling::table/descendant::tr"));

}
