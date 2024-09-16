package com.ricardorlg.vetclinc.utils;

public enum EndPoints {
    REGISTER_OWNER_PATH("/owners"),
    ALL_OWNERS_PATH("/owners/list"),
    GET_OWNER_BY_ID_PATH("/owners/{ownerId}"),
    PET_TYPES("/petTypes"),
    REGISTER_PET("/owners/{ownerId}/pets");

    private final String path;

    EndPoints(String path) {
        this.path = path;
    }

    public String getPath() {
        return path;
    }
}
