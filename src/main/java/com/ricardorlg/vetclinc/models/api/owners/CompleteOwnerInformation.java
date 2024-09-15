package com.ricardorlg.vetclinc.models.api.owners;

import com.ricardorlg.vetclinc.models.api.pets.CompletePetsInformation;

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
}
