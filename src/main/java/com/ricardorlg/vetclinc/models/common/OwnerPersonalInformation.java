package com.ricardorlg.vetclinc.models.common;

import com.ricardorlg.vetclinc.models.web.OwnerRowInformation;

public record OwnerPersonalInformation(
        String firstName,
        String lastName,
        String address,
        String city,
        String telephone
) {
    public OwnerRowInformation toOwnerRowInformation() {
        return new OwnerRowInformation(
                String.format("%s %s", firstName, lastName),
                address,
                city,
                telephone,
                ""
        );
    }
}
