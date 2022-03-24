package com.epam.cargo.filter;

import com.epam.cargo.entity.User;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Objects;
import java.util.Set;

import static com.epam.cargo.service.AuthorizationService.AUTHORIZED_USER;

public class AuthFilter implements Filter {

    private String contextPath = "/CargoDeliveryServlet";

    private Set<String> permitted = Set.of(
            contextPath + "/",
            contextPath + "/login",
            contextPath + "/directions",
            contextPath + "/registration",
            contextPath + "/forbidden",
            contextPath + "/fares");

    private Set<String> authManager = Set.of(
      contextPath + "/applications/review"
    );

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) servletRequest;
        HttpServletResponse res = (HttpServletResponse) servletResponse;
        User authorized = (User) req.getSession().getAttribute(AUTHORIZED_USER);

        String requestURI = req.getRequestURI();

        if (Objects.isNull(authorized)){
            if (!permitted.contains(requestURI)){
                res.sendRedirect(contextPath + "/login");
                return;
            }
        } else {
            if (authManager.contains(requestURI) && !authorized.isManager()){
                res.sendRedirect(contextPath + "/forbidden");
                return;
            }

        }

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
