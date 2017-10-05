package org.dudar.controller.constants;

import java.util.Locale;

public enum LocaleEnum {

    EN(new Locale("en", "GB"), "en_GB"), RU(new Locale("ru", "RU"), "ru_RU");

    private final static Locale DEFAULT_LOCALE = EN.getLocale();

    private Locale locale;
    private String lang;

    LocaleEnum(Locale locale, String lang) {
        this.locale = locale;
        this.lang = lang;
    }

    public Locale getLocale() {
        return locale;
    }
    public String getLang() {
        return lang;
    }

    public static Locale getDefault() {
        return DEFAULT_LOCALE;
    }

    public static Locale getLocaleByLang(String lang) {
        for (LocaleEnum le: LocaleEnum.values())
        {
            if(lang.equals(le.getLang()))
                return le.getLocale();
        }
        return getDefault();
    }
}