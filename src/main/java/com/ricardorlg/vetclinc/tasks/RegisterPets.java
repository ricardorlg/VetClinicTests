package com.ricardorlg.vetclinc.tasks;

import com.ricardorlg.vetclinc.models.common.PetInformation;
import com.ricardorlg.vetclinc.utils.Constants;
import net.serenitybdd.markers.IsHidden;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Task;

import java.util.List;

public class RegisterPets implements Task, IsHidden {
    private final String ownerFirstName;
    private final String ownerLastName;
    private final List<PetInformation> pets;

    public RegisterPets(String ownerFirstName, String ownerLastName, List<PetInformation> pets) {
        this.ownerFirstName = ownerFirstName;
        this.ownerLastName = ownerLastName;
        this.pets = pets;
    }

    public static RegisterPets forOwner(String ownerFirstName, String ownerLastName, List<PetInformation> pets) {
        return new RegisterPets(ownerFirstName, ownerLastName, pets);
    }

    @Override
    public <T extends Actor> void performAs(T actor) {
        var petsWithParsedBirthDates = pets.stream()
                .map(pet -> pet.withParsedBirthDate("MM-dd-yyyy"))
                .toList();
        for (PetInformation pet : pets) {
            actor.attemptsTo(
                    RegisterPet.forOwner(ownerFirstName, ownerLastName)
                            .withPetInformation(pet)
            );
            actor.remember(Constants.REGISTERED_PETS, petsWithParsedBirthDates);
        }
    }
}
