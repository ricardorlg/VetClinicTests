package com.ricardorlg.vetclinc.ui;

import net.serenitybdd.screenplay.targets.Target;
import net.serenitybdd.screenplay.ui.InputField;
import org.openqa.selenium.By;

public class AllOwnersPage {
    public static final Target OWNERS_TABLE = Target.the("the owners information table").located(By.cssSelector("owner-list table"));
    public static final Target SEARCH_FILTER = InputField.withPlaceholder("Search Filter");

}
