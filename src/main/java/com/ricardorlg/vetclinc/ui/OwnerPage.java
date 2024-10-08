package com.ricardorlg.vetclinc.ui;

import net.serenitybdd.screenplay.targets.Target;
import net.serenitybdd.screenplay.ui.Button;
import net.serenitybdd.screenplay.ui.Link;
import org.openqa.selenium.By;

public final class OwnerPage {
    public static final String PAGE_TITLE = "Owner Information";
    public static final Target PETS_AND_VISITS_DATA = Target.the("the pets and visits").located(By.xpath("//h2[text()='Pets and Visits']/following-sibling::table/tbody/tr"));
    public static final Target NAME_FIELD = Target.the("name field").located(By.xpath("//h2[text()='Owner Information']/following-sibling::table//th[text()='Name']/following-sibling::td"));
    public static final Target ADDRESS_FIELD = Target.the("address field").located(By.xpath("//h2[text()='Owner Information']/following-sibling::table//th[text()='Address']/following-sibling::td"));
    public static final Target CITY_FIELD = Target.the("city field").located(By.xpath("//h2[text()='Owner Information']/following-sibling::table//th[text()='City']/following-sibling::td"));
    public static final Target TELEPHONE_FIELD = Target.the("telephone field").located(By.xpath("//h2[text()='Owner Information']/following-sibling::table//th[text()='Telephone']/following-sibling::td"));
    public static final Target EDIT_OWNER_BUTTON = Link.withText("Edit Owner");
    public static final Target ADD_NEW_PET_BUTTON = Link.withText("Add New Pet");
    public static final Target PET_WITH_NAME_LINK = Link.withText("{0}");
    public static final Target ADD_VISIT_FOR_PET_BUTTON = Button.locatedBy("//h2[text()='Pets and Visits']/following-sibling::table/descendant::tr[descendant::a[text()='{0}']]/descendant::a[text()='Add Visit']");
}
