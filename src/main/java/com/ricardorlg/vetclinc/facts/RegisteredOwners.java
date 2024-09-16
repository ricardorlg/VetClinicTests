package com.ricardorlg.vetclinc.facts;

import com.ricardorlg.vetclinc.models.common.OwnerPersonalInformation;
import com.ricardorlg.vetclinc.tasks.RegisterOwner;
import com.ricardorlg.vetclinc.utils.Constants;
import net.serenitybdd.model.exceptions.TestCompromisedException;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Forget;
import net.serenitybdd.screenplay.RememberThat;
import net.serenitybdd.screenplay.conditions.Check;
import net.serenitybdd.screenplay.facts.Fact;
import net.serenitybdd.screenplay.questions.TheMemory;
import net.serenitybdd.screenplay.rest.interactions.Ensure;

import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import static com.ricardorlg.vetclinc.questions.OwnerQuestions.theRegisteredOwnersInTheSystem;

public final class RegisteredOwners implements Fact {
    private final List<OwnerPersonalInformation> registeredOwners;
    private static final Lock lock = new ReentrantLock();

    private RegisteredOwners(List<OwnerPersonalInformation> registeredOwners) {
        this.registeredOwners = registeredOwners;
    }

    public static RegisteredOwners from(List<OwnerPersonalInformation> registeredOwners) {
        return new RegisteredOwners(registeredOwners);
    }

    @Override
    public void setup(Actor actor) {
        boolean isUsingFeatureLevelContainer = actor.recall(Constants.IS_USING_FEATURE_LEVEL_CONTAINER);
        if (isUsingFeatureLevelContainer) {
            lock.lock();
        }
        try {
            var currentRegisteredOwners = actor.asksFor(theRegisteredOwnersInTheSystem());
            for (var owner : registeredOwners) {
                var isRegistered = currentRegisteredOwners.stream()
                        .anyMatch(currentOwner -> currentOwner.firstName().equals(owner.firstName())
                                && currentOwner.lastName().equals(owner.lastName())
                                && currentOwner.telephone().equals(owner.telephone()));
                if (!isRegistered) {
                    actor.attemptsTo(
                            Check.whether(TheMemory.withKey(Constants.USE_WEB_FORM_KEY).isPresent())
                                    .andIfSo(
                                            Forget.theValueOf(Constants.USE_WEB_FORM_KEY),
                                            RegisterOwner.withInformation(owner).withNoReporting(),
                                            Ensure.silentlyThat(response -> response.statusCode(201)),
                                            RememberThat.theValueOf(Constants.USE_WEB_FORM_KEY).is(true)
                                    )
                                    .otherwise(
                                            RegisterOwner.withInformation(owner).withNoReporting(),
                                            Ensure.silentlyThat(response -> response.statusCode(201))
                                    ),
                            RememberThat.theValueOf(Constants.REGISTERED_OWNERS).is(registeredOwners)
                    );
                }
            }
        } catch (Throwable e) {
            throw new TestCompromisedException("An error occurred while registering the owners", e);
        } finally {
            if (isUsingFeatureLevelContainer) {
                lock.unlock();
            }
        }
    }
}