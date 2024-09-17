package com.ricardorlg.vetclinc.models.api;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@SuppressWarnings("unused")
@JsonIgnoreProperties(ignoreUnknown = true)
public record ErrorsItem(
        List<String> codes,
        boolean bindingFailure,
        String code,
        String field,
        String defaultMessage,
        String objectName,
        String rejectedValue
) {
}