package org.dudar.model.entity.enums;

public enum Availability {

    READING_ROOM("enum.status.reading_room"), SUBSCRIPTION("enum.status.subscription");



    private String localeKey;

    Availability(String localeKey) {
        this.localeKey = localeKey;
    }

    public String getLocaleKey() {
        return localeKey;
    }

}
