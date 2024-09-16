package com.ricardorlg.vetclinc.ui;

import net.serenitybdd.screenplay.targets.Target;
import net.serenitybdd.screenplay.ui.Button;
import net.serenitybdd.screenplay.ui.InputField;
import net.serenitybdd.screenplay.ui.PageElement;

import static org.openqa.selenium.By.tagName;
import static org.openqa.selenium.By.xpath;
import static org.openqa.selenium.support.locators.RelativeLocator.with;

public final class PetPage {
    public static final String PAGE_TITLE = "Pet Information";
    public static final Target OWNER_NAME_FIELD = PageElement.containingText("{0}");
    public static final Target PET_NAME_FIELD = InputField.withNameOrId("name");
    public static final Target PET_BIRTH_DATE_FIELD = Target.the("the birth date field").located(with(tagName("input")).toRightOf(xpath("//label[text()='Birth date']")));
    public static final Target PET_TYPE_SELECT = Target.the("the type dropdown").located(with(tagName("select")).toRightOf(xpath("//label[text()='Type']")));
    public static final Target SUBMIT_BUTTON = Button.withText("Submit");
}
