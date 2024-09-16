package com.ricardorlg.vetclinc.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.actors.OnStage;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public final class Utils {
    public static String parseDate(String dateToParse) {
        if (dateToParse == null || dateToParse.trim().isEmpty()) {
            throw new IllegalArgumentException("dateToParse cannot be null or empty");
        }

        DateTimeFormatter inputFormatter = DateTimeFormatter.ofPattern("yyyy MMM dd");
        DateTimeFormatter outputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        try {
            LocalDate date = LocalDate.parse(dateToParse, inputFormatter);
            return date.format(outputFormatter);
        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException("Invalid date format: " + dateToParse, e);
        }
    }

    public static String prettyPrint(Object data) throws JsonProcessingException {
        if (data == null ) {
            throw new IllegalArgumentException("text cannot be null or empty");
        }
        var objectMapper = new ObjectMapper();
        return objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(data);
    }

    public static void actorTakesTheSpotlight(Actor actor) {
        OnStage.theActorCalled(actor.getName()).entersTheScene();
    }
}