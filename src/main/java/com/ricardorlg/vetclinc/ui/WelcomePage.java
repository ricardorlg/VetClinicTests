package com.ricardorlg.vetclinc.ui;

import net.serenitybdd.screenplay.targets.Target;
import net.serenitybdd.screenplay.ui.Link;
import net.serenitybdd.screenplay.ui.PageElement;
import org.openqa.selenium.By;

public final class WelcomePage {
    public static Target WELCOME_MESSAGE = PageElement.containingText("Welcome to Petclinic");
    public static Target OWNERS_MENU = Link.withText("Owners").called("the Owners menu");
    public static Target REGISTER_OWNER_BUTTON = Target.the("the Register button").located(By.cssSelector("a[ui-sref='ownerNew']"));
}
