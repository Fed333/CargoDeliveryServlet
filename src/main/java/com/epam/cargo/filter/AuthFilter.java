package com.epam.cargo.filter;

import com.epam.cargo.entity.User;
import org.fed333.servletboot.context.ApplicationContext;
import org.fed333.servletboot.source.properties.PropertiesSource;
import org.apache.log4j.Logger;
import org.jetbrains.annotations.NotNull;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Objects;
import java.util.Set;

import static org.fed333.servletboot.source.properties.constant.ApplicationPropertiesConstants.HTTP_PREFIX;
import static org.fed333.servletboot.source.properties.constant.PropertiesPathConstants.APPLICATION_PROPERTIES;
import static com.epam.cargo.listener.ContextListener.APPLICATION_CONTEXT_ATTRIBUTE;
import static com.epam.cargo.service.AuthorizationService.AUTHORIZED_USER;

/**
 * Filter designed for implementation authorization and authentication security.<br>
 * @author Roman Kovalchuk
 * @version 1.0
 * */
public class AuthFilter implements Filter {

    private final Logger logger = Logger.getLogger(AuthFilter.class);

    private String contextPath = "";

    /**
     * Set with allowed pages for authorized and unauthorized users.<br>
     * */
    private Set<String> permittedAll;

    /**
     * Set with allowed pages only for manager users.<br>
     * */
    private Set<String> permittedManager;

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) servletRequest;
        HttpServletResponse res = (HttpServletResponse) servletResponse;
        User authorized = (User) req.getSession().getAttribute(AUTHORIZED_USER);

        String requestURI = req.getRequestURI();

        if (Objects.isNull(authorized)){
            if (!permittedAll.contains(requestURI)){
                res.sendRedirect(contextPath + "/login");
                return;
            }
        } else {
            if (permittedManager.contains(requestURI) && !authorized.isManager()){
                res.sendRedirect(contextPath + "/forbidden");
                return;
            }

        }

        filterChain.doFilter(servletRequest, servletResponse);
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        ApplicationContext context = (ApplicationContext) filterConfig.getServletContext().getAttribute(APPLICATION_CONTEXT_ATTRIBUTE);
        try {
            contextPath = context.getObject(PropertiesSource.class).getProperties(APPLICATION_PROPERTIES.getPath()).getProperty(HTTP_PREFIX.getKey(), "");
        } catch (IOException e) {
            e.printStackTrace();
            logger.warn("Properties file " + APPLICATION_PROPERTIES.getPath() + " wasn't found! ContextPath will be \"\".", e);
        }

        permittedAll = getPermittedAll();
        permittedManager = getPermittedManager();
    }

    @NotNull
    private Set<String> getPermittedManager() {
        return Set.of(
                contextPath + "/applications/review",
                contextPath + "/profile/review",
                contextPath + "/application/accept",
                contextPath + "/application/complete",
                contextPath + "/application/reject"
        );
    }

    @NotNull
    private Set<String> getPermittedAll() {
        return Set.of(
                contextPath + "/",
                contextPath + "/login",
                contextPath + "/directions",
                contextPath + "/registration",
                contextPath + "/forbidden",
                contextPath + "/error",
                contextPath + "/fares",
                contextPath + "/delivery_cost_calculator"
        );
    }

    @Override
    public void destroy() {
        Filter.super.destroy();
    }
}