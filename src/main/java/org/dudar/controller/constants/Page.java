package org.dudar.controller.constants;

public final class Page {

    public static String PREFIX = "/WEB-INF/pages/";
    public static String SUFFIX = ".jsp";

    public static String HOME = "/index" + SUFFIX;
    public static String PAGE_NOT_FOUND = PREFIX + "pageNotFound" + SUFFIX;

    public static String LOGIN_PAGE = PREFIX + "login" + SUFFIX;
    public static String CATALOG = PREFIX + "catalog" + SUFFIX;
    public static String ORDER = PREFIX + "order" + SUFFIX;
    public static String SIGN_UP = PREFIX + "signUp" + SUFFIX;
    public static String FIND_ORDER = PREFIX + "findOrder" + SUFFIX;
    public static String CONFIRM_ORDER = PREFIX + "confirmOrder" + SUFFIX;
    public static String SEARCH_BOOK = PREFIX + "searchBook" + SUFFIX;
    public static String ADD_BOOK = PREFIX + "addBook" + SUFFIX;


    private Page() {}
}