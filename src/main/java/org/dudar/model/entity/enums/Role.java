package org.dudar.model.entity.enums;

public enum Role {

    READER("enum.role.reader"), LIBRARIAN("enum.role.librarian"), ADMIN("enum.role.admin");

    private String localeKey;

    Role(String localeKey) {
        this.localeKey = localeKey;
    }

    public String getLocaleKey() {
        return localeKey;
    }

}
