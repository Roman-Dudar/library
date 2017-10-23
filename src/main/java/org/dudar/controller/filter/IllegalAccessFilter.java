package org.dudar.controller.filter;

import org.apache.log4j.Logger;
import org.dudar.controller.constants.Page;
import org.dudar.controller.constants.Parameters;
import org.dudar.controller.constants.UrlMap;
import org.dudar.model.entity.User;
import org.dudar.model.entity.enums.Role;
import org.dudar.utils.locale.LocaleMessage;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebFilter(urlPatterns = { "/main/*" })
public class IllegalAccessFilter implements Filter {

    private final static Logger LOGGER = Logger.getLogger(IllegalAccessFilter.class);

    public void init(FilterConfig filterConfig) throws ServletException {
    }

    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
            throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) servletRequest;
        HttpServletResponse httpResponse = (HttpServletResponse) servletResponse;

        User user = (User) httpRequest.getSession().getAttribute(Parameters.USER);

        if (!isPathLegalForUser(httpRequest, user)) {
            httpRequest.setAttribute(Parameters.ERROR, LocaleMessage.ILLEGAL_ACCESS);
            httpRequest.getRequestDispatcher(Page.LOGIN_PAGE).forward(servletRequest, servletResponse);
        }
        filterChain.doFilter(servletRequest, servletResponse);
    }

    public void destroy() {
    }

    private boolean isUserSignIn(User user) {
        return user != null;
    }

    private boolean isPathLegalForUser(HttpServletRequest request, User user) {
        String url = request.getRequestURI().replace("/library/main", "");
        if (isPublicPage(url)) {
            return true;
        }
        if (user == null) return false;
        return (isReaderPage(url) && (user.getRole().equals(Role.READER) ||
                    user.getRole().equals(Role.LIBRARIAN) || user.getRole().equals(Role.ADMIN)))
                || (isLibrarianPage(url) && (user.getRole().equals(Role.LIBRARIAN)
                    || user.getRole().equals(Role.ADMIN)))
                || (isAdminPage(url) && user.getRole().equals(Role.ADMIN));
    }

    private boolean isPublicPage(String url) {
        switch(url) {
            case UrlMap.SEARCH_BOOK:
            case UrlMap.LOGOUT:
            case UrlMap.MAIN:
            case UrlMap.LOGIN:
            case UrlMap.CATALOG:
            case UrlMap.LOCALE: return true;
            default: return false;
        }
    }

    private boolean isReaderPage(String url) {
        switch(url) {
            case UrlMap.ORDER: return true;
            default: return false;
        }
    }

    private boolean isLibrarianPage(String url) {
        switch(url) {
            case UrlMap.ADD_BOOK:
            case UrlMap.FIND_ORDER:
            case UrlMap.CONFIRM_ORDER:
            case UrlMap.SIGN_UP: return true;
            default: return false;
        }
    }

    private boolean isAdminPage(String url) {
        switch(url) {
            default: return false;
        }
    }


}