package com.ricardorlg.vetclinc.ui;

import net.serenitybdd.screenplay.targets.Target;
import net.serenitybdd.screenplay.ui.Button;
import net.serenitybdd.screenplay.ui.InputField;
import net.serenitybdd.screenplay.ui.PageElement;
import org.openqa.selenium.By;

public final class PetPage {
    public static final String PAGE_TITLE = "Pet Information";
    public static final Target OWNER_NAME_FIELD = PageElement.containingText("{0}");
    public static final Target PET_NAME_FIELD = InputField.withNameOrId("name");
    public static final Target PET_BIRTH_DATE_FIELD = Target.the("the birth date field").located(By.cssSelector("input[type='date']"));
    public static final Target PET_TYPE_SELECT = Target.the("the type dropdown").located(By.cssSelector("select"));
    public static final Target SUBMIT_BUTTON = Button.withText("Submit");
}
