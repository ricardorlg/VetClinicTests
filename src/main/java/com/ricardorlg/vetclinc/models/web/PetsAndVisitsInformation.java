package com.ricardorlg.vetclinc.models.web;

import com.ricardorlg.vetclinc.models.common.PetInformation;
import com.ricardorlg.vetclinc.models.common.VisitInformation;

import java.util.List;

public record PetsAndVisitsInformation(
        PetInformation petInformation,
        List<VisitInformation> visitInformationList
) {
}
