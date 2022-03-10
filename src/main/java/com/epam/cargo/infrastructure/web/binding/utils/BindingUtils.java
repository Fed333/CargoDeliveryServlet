package com.epam.cargo.infrastructure.web.binding.utils;

import com.epam.cargo.infrastructure.context.ApplicationContext;
import com.epam.cargo.infrastructure.format.formatter.Formatter;
import com.epam.cargo.infrastructure.format.manager.FormatterManager;

import javax.servlet.http.HttpServletRequest;
import java.util.Objects;

public class BindingUtils {

    @SuppressWarnings("unchecked")
    public static <T> T bindType(Class<T> bindClass, HttpServletRequest req, String parameterName, ApplicationContext context) {
        Object bindValue = req.getParameter(parameterName);
        if (Objects.nonNull(bindValue) && !bindClass.isAssignableFrom(bindValue.getClass())){
            if (!((String)bindValue).isBlank()) {
                bindValue = convertTypeFromString((String) bindValue, bindClass, context.getObject(FormatterManager.class));
            } else {
                bindValue = null;
            }
        }
        return (T) bindValue;
    }

    private static <T> T convertTypeFromString(String value, Class<T> fieldClass, FormatterManager manager) {
        Formatter<String, T> formatter = manager.getFormatter(String.class, fieldClass).orElseThrow(()->new RuntimeException(String.format("No formatter from %s to %s was found! Please specify one with %s ", String.class, fieldClass, FormatterManager.class)));
        return formatter.format(value);
    }

}
