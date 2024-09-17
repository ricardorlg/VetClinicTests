package com.ricardorlg.vetclinc.ui;

import net.serenitybdd.screenplay.targets.Target;
import org.openqa.selenium.By;

public final class VeterinariansPage {
    public static final Target DISPLAYED_VETERINARIANS_TABLE = Target.the("the Veterinarians table")
            .located(By.xpath("//h2[text()='Veterinarians']/following-sibling::table"));
}
