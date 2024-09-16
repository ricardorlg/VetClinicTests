package com.ricardorlg.vetclinc.models.api.pets;

public record CreatePetRequestBody(
        String name,
        String birthDate,
        String typeId
) { }
