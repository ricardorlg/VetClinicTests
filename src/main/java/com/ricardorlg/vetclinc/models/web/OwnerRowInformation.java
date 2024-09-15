package com.ricardorlg.vetclinc.models.web;

import com.ricardorlg.vetclinc.models.common.OwnerPersonalInformation;

public record OwnerRowInformation(
        String name,
        String address,
        String city,
        String telephone,
        String pets
) { }
