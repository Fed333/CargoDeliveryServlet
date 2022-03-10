package com.epam.cargo.infrastructure.web.binding.binder.impl;

import com.epam.cargo.infrastructure.annotation.RequestParam;
import com.epam.cargo.infrastructure.context.ApplicationContext;
import com.epam.cargo.infrastructure.format.formatter.Formatter;
import com.epam.cargo.infrastructure.format.manager.FormatterManager;
import com.epam.cargo.infrastructure.web.binding.binder.ParameterBinder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Parameter;
import java.util.Objects;

import static com.epam.cargo.infrastructure.web.binding.utils.BindingUtils.bindType;

/**
 * Binds web parameters annotated with @RequestParam annotation.
 * @author Roman Kovalchuk
 * @see ParameterBinder
 * @see RequestParam
 * @version 1.0
 * */
public class RequestParamAnnotationParameterBinder implements ParameterBinder {

    @Override
    public Object bindParameter(Parameter parameter, HttpServletRequest req, HttpServletResponse res, ApplicationContext context) {
        RequestParam paramAnnotation = parameter.getAnnotation(RequestParam.class);
        Class<?> parameterClass = parameter.getType();
        Object bind = bindType(parameterClass, req, paramAnnotation.name(), context);
        if (Objects.isNull(bind)){
            if (parameterClass.isAssignableFrom(paramAnnotation.defaultValue().getClass())){
                return paramAnnotation.defaultValue();
            } else {
                Formatter<String, ?> formatter = context.getObject(FormatterManager.class).getFormatter(String.class, parameterClass).orElseThrow();
                return formatter.format(paramAnnotation.defaultValue());
            }
        }
        return bind;

    }

    @Override
    public boolean matchesParameter(Parameter parameter) {
        return parameter.isAnnotationPresent(RequestParam.class);
    }
}
