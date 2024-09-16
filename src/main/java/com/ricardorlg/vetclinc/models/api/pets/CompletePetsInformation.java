package com.ricardorlg.vetclinc.models.api.pets;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.ricardorlg.vetclinc.models.api.visits.CompleteVisitInformation;
import com.ricardorlg.vetclinc.models.common.PetInformation;
import com.ricardorlg.vetclinc.models.web.PetsAndVisitsInformation;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public record CompletePetsInformation(
        int id,
        String name,
        String birthDate,
        Type type,
        List<CompleteVisitInformation> visits
) {
    public PetsAndVisitsInformation toPetsAndVisitsInformation() {
        return new PetsAndVisitsInformation(
                new PetInformation(name, birthDate, type.name),
                visits.stream()
                        .map(CompleteVisitInformation::toVisitInformation)
                        .toList()
        );
    }

    public record Type(int id, String name) {
    }
}
