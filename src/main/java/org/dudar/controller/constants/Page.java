package org.dudar.controller.constants;

public final class Page {

    private Page() {
    }

    public static String PREFIX = "/WEB-INF/pages/";
    public static String SUFFIX = ".jsp";

    public static String HOME = "/index" + SUFFIX;
    public static String PAGE_NOT_FOUND = PREFIX + "pageNotFound" + SUFFIX;

    public static String CHANGE_PASSWORD_VIEW = PREFIX + "changePassword" + SUFFIX;
    public static String LOGIN_PAGE = PREFIX + "login" + SUFFIX;
}
