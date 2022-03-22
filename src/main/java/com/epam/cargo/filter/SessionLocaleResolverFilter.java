package com.epam.cargo.filter;

import com.epam.cargo.infrastructure.context.ApplicationContext;
import com.epam.cargo.service.LocaleResolverService;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

import static com.epam.cargo.listener.ContextListener.APPLICATION_CONTEXT_ATTRIBUTE;

/**
 * Filter for switching language locale.<br>
 * @author Roman Kovalchuk
 * @version 1.0
 * */
public class SessionLocaleResolverFilter implements Filter {

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) servletRequest;
        ApplicationContext context = (ApplicationContext) req.getServletContext().getAttribute(APPLICATION_CONTEXT_ATTRIBUTE);

        LocaleResolverService localeResolverService = context.getObject(LocaleResolverService.class);
        localeResolverService.interceptLocale(req);

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
