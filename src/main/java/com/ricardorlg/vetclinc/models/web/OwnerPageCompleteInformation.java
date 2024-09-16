package com.ricardorlg.vetclinc.models.web;

import java.util.List;

public record OwnerPageCompleteInformation(
        String ownerFullName,
        String ownerAddress,
        String ownerCity,
        String ownerTelephone,
        List<PetsAndVisitsInformation> petsAndVisitsInformationList
) {
}
