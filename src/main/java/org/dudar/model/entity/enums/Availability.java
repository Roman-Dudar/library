package org.dudar.model.entity.enums;

public enum Availability {

    READING_ROOM("library.availability.reading_room"), SUBSCRIPTION("library.availability.subscription");



    private String localeKey;

    Availability(String localeKey) {
        this.localeKey = localeKey;
    }

    public String getLocaleKey() {
        return localeKey;
    }

}
