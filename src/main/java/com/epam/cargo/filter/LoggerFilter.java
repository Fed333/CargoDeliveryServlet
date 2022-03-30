package com.epam.cargo.filter;

import org.apache.log4j.Logger;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.time.Duration;
import java.time.LocalTime;

public class LoggerFilter implements Filter {

    private final Logger logger = Logger.getLogger(LoggerFilter.class);

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        logger.info(String.format("HttpMethod: [%1$s]. Mapping: [%2$s] start.", request.getMethod(), request.getRequestURI()));

        LocalTime before = LocalTime.now();
        filterChain.doFilter(servletRequest, servletResponse);
        LocalTime after = LocalTime.now();

        Duration duration = Duration.between(before, after);
        logger.info(String.format("HttpMethod: [%1$s]. Mapping: [%2$s] end.", request.getMethod(), request.getRequestURI()));
        logger.info(String.format("Duration: %s nanos.", duration.toNanos()));
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void destroy() {
        Filter.super.destroy();
    }

}
