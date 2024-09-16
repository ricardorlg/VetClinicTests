package com.ricardorlg.vetclinc.ui;

import net.serenitybdd.screenplay.targets.Target;
import net.serenitybdd.screenplay.ui.Button;
import net.serenitybdd.screenplay.ui.InputField;

public final class EditOwnerPage {
    public static final Target FIRST_NAME_FIELD = InputField.withNameOrId("firstName");
    public static final Target LAST_NAME_FIELD = InputField.withNameOrId("lastName");
    public static final Target ADDRESS_FIELD = InputField.withNameOrId("address");
    public static final Target CITY_FIELD = InputField.withNameOrId("city");
    public static final Target TELEPHONE_FIELD = InputField.withNameOrId("telephone");
    public static final Target SUBMIT_BUTTON = Button.withText("Submit");
}
