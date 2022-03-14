package com.epam.cargo.infrastructure.web.binding.binder.impl;

import com.epam.cargo.infrastructure.context.ApplicationContext;
import com.epam.cargo.infrastructure.web.binding.binder.ParameterBinder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.lang.reflect.Parameter;

/**
 * Designed for binding HttpSession to controllers methods.
 * @author Roman Kovalchuk
 * @version 1.0
 * */
public class HttpSessionParameterBinder implements ParameterBinder {

    @Override
    public Object bindParameter(Parameter parameter, HttpServletRequest req, HttpServletResponse res, ApplicationContext context) {
        return req.getSession();
    }

    @Override
    public boolean matchesParameter(Parameter parameter) {
        return HttpSession.class.isAssignableFrom(parameter.getType());
    }
}
