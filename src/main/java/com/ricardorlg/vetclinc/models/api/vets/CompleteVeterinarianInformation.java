package com.ricardorlg.vetclinc.models.api.vets;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public record CompleteVeterinarianInformation(
        int id,
        String firstName,
        String lastName,
        @JsonProperty("specialties")
        List<VeterinarianSpeciality> specialities,
        int nrOfSpecialties
) {
}
