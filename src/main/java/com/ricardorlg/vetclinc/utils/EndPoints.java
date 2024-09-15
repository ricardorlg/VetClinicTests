package com.ricardorlg.vetclinc.utils;

public enum EndPoints {
    REGISTER_OWNER_PATH("/owners"),
    ALL_OWNERS_PATH("/owners/list");

    private final String path;

    EndPoints(String path) {
        this.path = path;
    }

    public String getPath() {
        return path;
    }
}
