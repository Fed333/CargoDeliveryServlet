package com.epam.cargo.infrastructure.web.binding.binder.impl;

import com.epam.cargo.infrastructure.annotation.DTO;
import com.epam.cargo.infrastructure.annotation.DataTransfer;
import com.epam.cargo.infrastructure.context.ApplicationContext;
import com.epam.cargo.infrastructure.web.binding.binder.ParameterBinder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Parameter;
import java.util.Objects;

import static com.epam.cargo.infrastructure.web.binding.utils.BindingUtils.bindType;
import static com.epam.cargo.infrastructure.web.binding.utils.BindingUtils.qualify;

/**
 * Binds web parameters of dto classes.
 * React to classes annotated with @DTO annotation or parameters with @DataTransfer.<br>
 * @author Roman Kovalchuk
 * @see ParameterBinder
 * @see DTO
 * @see DataTransfer
 * @version 1.1
 * */
public class DataTransferObjectParameterBinder implements ParameterBinder {

    @Override
    public Object bindParameter(Parameter parameter, HttpServletRequest req, HttpServletResponse res, ApplicationContext context) {
        Class<?> parameterClass = parameter.getType();
        return assembleDataTransfer(parameterClass, req, "", qualify(parameter), context);
    }

    @Override
    public boolean matchesParameter(Parameter parameter) {
        return parameter.getType().isAnnotationPresent(DTO.class) || parameter.isAnnotationPresent(DataTransfer.class);
    }

    private Object assembleDataTransfer(Class<?> transferClass, HttpServletRequest req, String parent, String qualifier, ApplicationContext context){
        try {
            Object transfer = transferClass.getDeclaredConstructor().newInstance();
            for (Field field : transfer.getClass().getDeclaredFields()) {
                Class<?> fieldClass = field.getType();

                Object fieldObject;
                if (fieldClass.isAnnotationPresent(DTO.class)){
                    fieldObject = assembleDataTransfer(fieldClass, req, buildParamName(parent, field), qualifier, context);
                } else {
                    fieldObject = bindType(fieldClass, req, qualifier + buildParamName(parent, field), context);

                }

                if (Objects.nonNull(fieldObject)) {
                    field.setAccessible(true);
                    field.set(transfer, fieldObject);
                }
            }
            return transfer;

        } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to create data transfer object of class " + transferClass);
        }
    }

    private String buildParamName(String parent, Field field){
        String prefix = Objects.nonNull(parent) && !parent.isBlank() ? parent + "." : "";
        return prefix + field.getName();
    }
}
