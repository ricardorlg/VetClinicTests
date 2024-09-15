package com.ricardorlg.vetclinc.models.api;

import java.util.List;

public record ErrorResponse(
        String path,
        String error,
        String message,
        List<ErrorsItem> errors,
        String timestamp,
        int status
) {
}