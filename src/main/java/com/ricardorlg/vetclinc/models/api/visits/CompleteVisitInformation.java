package com.ricardorlg.vetclinc.models.api.visits;

import com.ricardorlg.vetclinc.models.common.VisitInformation;

@SuppressWarnings("unused")
public record CompleteVisitInformation(
        int id,
        String date,
        String description
) {
    public VisitInformation toVisitInformation() {
        return new VisitInformation(date, description);
    }
}
