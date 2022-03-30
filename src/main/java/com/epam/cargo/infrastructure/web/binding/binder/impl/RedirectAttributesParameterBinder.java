package com.epam.cargo.infrastructure.web.binding.binder.impl;

import com.epam.cargo.infrastructure.context.ApplicationContext;
import com.epam.cargo.infrastructure.web.binding.binder.ParameterBinder;
import com.epam.cargo.infrastructure.web.redirect.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Parameter;
import java.util.Objects;

/**
 * Designed for binding {@link RedirectAttributes} to controllers methods.
 * @author Roman Kovalchuk
 * @version 1.0
 * */
public class RedirectAttributesParameterBinder implements ParameterBinder {

    @Override
    public Object bindParameter(Parameter parameter, HttpServletRequest req, HttpServletResponse res, ApplicationContext context) {
        if (Objects.isNull(req.getAttribute(RedirectAttributes.class.getName()))){
            req.setAttribute(RedirectAttributes.class.getName(), context.getObject(RedirectAttributes.class));
        }
        return req.getAttribute(RedirectAttributes.class.getName());
    }

    @Override
    public boolean matchesParameter(Parameter parameter) {
        return RedirectAttributes.class.isAssignableFrom(parameter.getType());
    }
}
