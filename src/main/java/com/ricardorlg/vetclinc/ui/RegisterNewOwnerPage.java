package com.ricardorlg.vetclinc.ui;

import net.serenitybdd.screenplay.targets.Target;
import net.serenitybdd.screenplay.ui.Button;
import net.serenitybdd.screenplay.ui.InputField;

public final class RegisterNewOwnerPage {

    public static Target FIRST_NAME_FIELD = InputField.withNameOrId("firstName");
    public static Target LAST_NAME_FIELD = InputField.withNameOrId("lastName");
    public static Target ADDRESS_FIELD = InputField.withNameOrId("address");
    public static Target CITY_FIELD = InputField.withNameOrId("city");
    public static Target PHONE_FIELD = InputField.withNameOrId("telephone");
    public static Target SUBMIT_BUTTON = Button.withText("Submit").called("the Submit button");
}
