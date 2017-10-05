package org.dudar.controller.util.filter;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import org.dudar.controller.constants.Parameters;
import org.dudar.model.entity.User;
import org.dudar.model.entity.enums.Role;
import org.dudar.utils.LocaleManager;
import org.dudar.utils.LocaleMessage;

@WebFilter(urlPatterns = { "/main/reader/*", "/main/librarian/*", "/main/admin/*" })
public class IllegalAccessFilter implements Filter {

    private final static Logger LOGGER = Logger.getLogger(IllegalAccessFilter.class);

    public void init(FilterConfig filterConfig) throws ServletException {
    }

    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
            throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) servletRequest;
        HttpServletResponse httpResponse = (HttpServletResponse) servletResponse;

        User user = (User) httpRequest.getSession().getAttribute(Parameters.USER);

        if (!isUserSignIn(user) || !isPathLegalForUser(httpRequest.getRequestURI(), user)) {
            LOGGER.info("Unauthorized access to the resource: " + httpRequest.getRequestURI());
            Map<String, String> message = new HashMap<>();
            message.put(Parameters.ERROR, LocaleManager.getString(LocaleMessage.ILLEGAL_ACCESS));
            return;
        }
        filterChain.doFilter(servletRequest, servletResponse);
    }

    public void destroy() {
    }

    private boolean isUserSignIn(User user) {
        return user != null;

    }

    private boolean isPathLegalForUser(String servletPath, User user) {
        return (isReaderPage(servletPath) && user.getRole().equals(Role.READER))
                || (isLibrarianPage(servletPath) && user.getRole().equals(Role.LIBRARIAN))
                || (isAdminPage(servletPath) && user.getRole().equals(Role.ADMIN));
    }

    private boolean isReaderPage(String requestURI) {
        return requestURI.contains(Role.READER.name().toLowerCase());
    }

    private boolean isLibrarianPage(String requestURI) {
        return requestURI.contains(Role.LIBRARIAN.name().toLowerCase());
    }

    private boolean isAdminPage(String requestURI) {
        return requestURI.contains(Role.ADMIN.name().toLowerCase());
    }

}