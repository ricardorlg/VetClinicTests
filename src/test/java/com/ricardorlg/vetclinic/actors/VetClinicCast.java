package com.ricardorlg.vetclinic.actors;

import com.ricardorlg.vetclinc.utils.Constants;
import com.ricardorlg.vetclinc.utils.DockerManager;
import io.cucumber.java.Scenario;
import net.serenitybdd.screenplay.Ability;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.abilities.BrowseTheWeb;
import net.serenitybdd.screenplay.actors.Cast;
import net.serenitybdd.screenplay.rest.abilities.CallAnApi;
import net.thucydides.core.webdriver.ThucydidesWebDriverSupport;
import net.thucydides.model.environment.SystemEnvironmentVariables;
import org.openqa.selenium.WebDriver;

@SuppressWarnings("unchecked")
public class VetClinicCast extends Cast {

    private final Scenario scenario;
    private final boolean useIsolatedContainer;

    public VetClinicCast(Scenario scenario, boolean useIsolatedContainer) {
        this.scenario = scenario;
        this.useIsolatedContainer = useIsolatedContainer;
    }

    @Override
    public Actor actorNamed(String actorName, Ability... abilities) {
        Actor actor = super.actorNamed(actorName, abilities);
        String baseUrl;
        if (useIsolatedContainer) {
            var container = DockerManager.startContainerForScenario(scenario.getName());
            baseUrl = DockerManager.getContainerBaseUrl(container);
        } else {
            baseUrl = DockerManager.getGlobalContainerBaseUrl();
        }
        if (actor.abilityTo(CallAnApi.class) == null) {
            actor.can(CallAnApi.at(baseUrl));
        }
        if (actor.abilityTo(BrowseTheWeb.class) == null) {
            actor.can(BrowseTheWeb.with(theDefaultBrowserFor(actorName)));
        }
        SystemEnvironmentVariables.currentEnvironmentVariables().setProperty("home.page", baseUrl);
        actor.remember(Constants.BASE_URL_KEY, baseUrl);
        return actor;
    }

    private WebDriver theDefaultBrowserFor(String actorName) {
        return ThucydidesWebDriverSupport.getWebdriverManager().getWebdriverByName(actorName);
    }
}
