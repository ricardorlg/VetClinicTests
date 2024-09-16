package com.ricardorlg.vetclinc.models.common;

import com.ricardorlg.vetclinc.models.api.owners.CompleteOwnerInformation;
import com.ricardorlg.vetclinc.models.web.OwnerPageCompleteInformation;
import com.ricardorlg.vetclinc.models.web.OwnerRowInformation;

import java.util.ArrayList;

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

    public OwnerPageCompleteInformation toOwnerPageCompleteInformation() {
        return new OwnerPageCompleteInformation(
                String.format("%s %s", firstName, lastName),
                address,
                city,
                telephone,
                new ArrayList<>()
        );
    }

    public CompleteOwnerInformation toCompleteOwnerInformation(int ownerId) {
        return new CompleteOwnerInformation(
                ownerId,
                firstName,
                lastName,
                address,
                city,
                telephone,
                new ArrayList<>()
        );
    }
}
