package com.ricardorlg.vetclinc.models.common;

import com.ricardorlg.vetclinc.models.api.pets.CompletePetsInformation;
import com.ricardorlg.vetclinc.models.web.PetsAndVisitsInformation;

import java.util.List;

import static com.ricardorlg.vetclinc.utils.Constants.COMMON_DATE_FORMAT;
import static com.ricardorlg.vetclinc.utils.Utils.parseDate;

public record PetInformation(
        String name,
        String birthDate,
        String type
) {
    public PetsAndVisitsInformation toPetsAndVisitsInformationWithNoVisits() {
        return new PetsAndVisitsInformation(this, List.of());
    }

    public PetInformation withParsedBirthDate(String fromFormat) {
        return new PetInformation(name, parseDate(birthDate, fromFormat, COMMON_DATE_FORMAT), type);
    }

    public CompletePetsInformation toCompletePetInformation(CompletePetsInformation.Type type) {
        return new CompletePetsInformation(-1, name, birthDate, type, List.of());
    }
}
