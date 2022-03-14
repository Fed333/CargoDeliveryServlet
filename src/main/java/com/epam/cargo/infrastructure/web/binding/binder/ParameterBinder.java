package com.epam.cargo.infrastructure.web.binding.binder;

import com.epam.cargo.infrastructure.context.ApplicationContext;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Parameter;

/**
 * Interface designed to bind web parameters from HttpServletRequest to methods of controllers.<br>
 * @author Roman Kovalchuk
 * @version 1.0
 * */
public interface ParameterBinder {

    /**
     * Binds web parameter.
     * */
    Object bindParameter(Parameter parameter, HttpServletRequest req, HttpServletResponse res, ApplicationContext context);

    /**
     * Condition to run binding on Parameter object.<br>
     * Checks whether current ParameterBinder is assigned to the given web parameter.<br>
     * @param parameter potential parameter to bind
     * @return true if parameter can be bind with this ParameterBinder, otherwise false
     * */
    boolean matchesParameter(Parameter parameter);
}
