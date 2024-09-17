package com.ricardorlg.vetclinc.models.common;

import static com.ricardorlg.vetclinc.utils.Constants.COMMON_DATE_FORMAT;
import static com.ricardorlg.vetclinc.utils.Utils.parseDate;

public record VisitInformation(
        String visitDate,
        String description
) {
    public VisitInformation withParsedVisitDate(String fromFormat) {
        return new VisitInformation(parseDate(visitDate, fromFormat, COMMON_DATE_FORMAT), description);
    }

    @Override
    public String toString() {
        return description + " visit on " + visitDate;
    }
}
