package org.dudar.model.entity.enums;

public enum Status {
    AVAILABLE("enum.status.available"), UNAVAILABLE("enum.status.unavailable");

    private String localeKey;

    Status(String localeKey) {
        this.localeKey = localeKey;
    }

    public String getLocaleKey() {
        return localeKey;
    }
}