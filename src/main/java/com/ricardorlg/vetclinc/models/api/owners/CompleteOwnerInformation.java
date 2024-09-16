package com.ricardorlg.vetclinc.models.api.owners;

import com.ricardorlg.vetclinc.models.api.pets.CompletePetsInformation;
import com.ricardorlg.vetclinc.models.web.OwnerPageCompleteInformation;

import java.util.List;

public record CompleteOwnerInformation(
        int id,
        String firstName,
        String lastName,
        String address,
        String city,
        String telephone,
        List<CompletePetsInformation> pets
) {
    public OwnerPageCompleteInformation toOwnerPageCompleteInformation() {
        return new OwnerPageCompleteInformation(
                String.format("%s %s", firstName, lastName),
                address,
                city,
                telephone,
                pets.stream()
                        .map(CompletePetsInformation::toPetsAndVisitsInformation)
                        .toList()
        );
    }

    public String fullName() {
        return String.format("%s %s", firstName, lastName);
    }
}
