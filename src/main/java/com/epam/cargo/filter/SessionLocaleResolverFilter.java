package com.epam.cargo.filter;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Optional;

/**
 * Filter for switching language locale.<br>
 * @author Roman Kovalchuk
 * @version 1.0
 * */
public class SessionLocaleResolverFilter implements Filter {

    private static final String DEFAULT_LOCALE_LANGUAGE = "en";
    private static final String LANG_ATTRIBUTE = "lang";

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) servletRequest;
        HttpSession session = req.getSession();

        String lang = Optional.ofNullable(req.getParameter(LANG_ATTRIBUTE)).orElse((String)session.getAttribute(LANG_ATTRIBUTE));
        req.getSession().setAttribute(LANG_ATTRIBUTE, Optional.ofNullable(lang).orElse(DEFAULT_LOCALE_LANGUAGE));
        filterChain.doFilter(servletRequest, servletResponse);
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void destroy() {
        Filter.super.destroy();
    }
}
