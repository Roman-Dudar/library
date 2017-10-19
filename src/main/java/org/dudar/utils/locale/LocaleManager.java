package org.dudar.utils.locale;

import java.util.Locale;
import java.util.ResourceBundle;

public class LocaleManager {

    public static String MESSAGES_BUNDLE_NAME = "/i18n/messages";
    public static ResourceBundle BUNDLE = ResourceBundle.getBundle(MESSAGES_BUNDLE_NAME,
                                                                   new Locale("en", "GB"));

    public static void setResourceBundleLocale(Locale locale) {
        BUNDLE = ResourceBundle.getBundle(MESSAGES_BUNDLE_NAME, locale);
    }

    public static String getString(String key) {
        return BUNDLE.getString(key);
    }

}
