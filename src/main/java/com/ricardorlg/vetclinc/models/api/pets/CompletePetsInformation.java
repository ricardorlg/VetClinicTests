package com.ricardorlg.vetclinc.models.api.pets;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record CompletePetsInformation(
        int id,
        String name,
        String birthDate,
        Type type
) {
    public record Type(int id, String name) {
    }
}
