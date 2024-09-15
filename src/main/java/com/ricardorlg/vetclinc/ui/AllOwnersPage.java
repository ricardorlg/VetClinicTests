package com.ricardorlg.vetclinc.ui;

import net.serenitybdd.screenplay.targets.Target;
import org.openqa.selenium.By;

public class AllOwnersPage {
    public static Target OWNERS_TABLE = Target.the("the owners information table").located(By.cssSelector("owner-list table"));

}
