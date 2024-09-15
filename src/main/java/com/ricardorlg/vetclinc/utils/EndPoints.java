package com.ricardorlg.vetclinc.utils;

public enum EndPoints {
    REGISTER_OWNER_PATH("/owners");

    private final String path;

    EndPoints(String path) {
        this.path = path;
    }

    public String getPath() {
        return path;
    }
}
