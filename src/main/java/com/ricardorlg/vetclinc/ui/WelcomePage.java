package com.ricardorlg.vetclinc.ui;

import net.serenitybdd.screenplay.targets.Target;
import net.serenitybdd.screenplay.ui.Link;
import net.serenitybdd.screenplay.ui.PageElement;
import org.openqa.selenium.By;

public final class WelcomePage {
    public static final String PAGE_TITLE = "Welcome";
    public static final Target WELCOME_MESSAGE = PageElement.containingText("Welcome to Petclinic");
    public static final Target OWNERS_MENU = Link.withText("Owners").called("the Owners menu");
    public static final Target REGISTER_OWNER_BUTTON = Target.the("the Register button").located(By.cssSelector("a[ui-sref='ownerNew']"));
    public static final Target ALL_OWNERS_BUTTON = Target.the("the All Owners button").located(By.cssSelector("a[ui-sref='owners']"));
    public static final Target VETERINARIANS_MENU = Link.withTitle("veterinarians");
}
