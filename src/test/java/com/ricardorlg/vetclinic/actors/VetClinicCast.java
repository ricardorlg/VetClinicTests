package com.ricardorlg.vetclinic.actors;

import com.ricardorlg.vetclinc.utils.Constants;
import com.ricardorlg.vetclinc.utils.DockerManager;
import io.cucumber.java.Scenario;
import net.serenitybdd.model.buildinfo.BuildInfo;
import net.serenitybdd.screenplay.Ability;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.abilities.BrowseTheWeb;
import net.serenitybdd.screenplay.actors.Cast;
import net.serenitybdd.screenplay.rest.abilities.CallAnApi;
import net.thucydides.core.webdriver.ThucydidesWebDriverSupport;
import net.thucydides.model.environment.SystemEnvironmentVariables;
import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.WebDriver;

@SuppressWarnings("unchecked")
public class VetClinicCast extends Cast {
    private final Scenario scenario;
    private final boolean withGlobalContainer;
    private final boolean withFeatureLevelContainer;

    public VetClinicCast(Scenario scenario) {
        this(scenario, true, false);
    }

    public VetClinicCast(Scenario scenario, boolean withGlobalContainer, boolean withFeatureLevelContainer) {
        this.scenario = scenario;
        this.withGlobalContainer = withGlobalContainer;
        this.withFeatureLevelContainer = withFeatureLevelContainer;
    }


    @Override
    public Actor actorNamed(String actorName, Ability... abilities) {
        Actor actor = super.actorNamed(actorName, abilities);
        String baseUrl;
        if (withGlobalContainer) {
            baseUrl = DockerManager.getGlobalContainerBaseUrl();
            BuildInfo.section("Docker Container Information")
                    .setProperty("Global URL", baseUrl);
        } else if (withFeatureLevelContainer) {
            var featureName = StringUtils.substringAfterLast(scenario.getUri().toString(), "/");
            var container = DockerManager.startContainerFor(featureName);
            baseUrl = DockerManager.getContainerBaseUrl(container);
            BuildInfo.section("Docker Container Information")
                    .setProperty(featureName + " URL", baseUrl);
        } else {
            throw new IllegalStateException("At least one of the flags withGlobalContainer or withFeatureLevelContainer must be true");
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

    @Override
    public void dismissAll() {
        DockerManager.stopContainerFor(scenario.getName());
    }
}
