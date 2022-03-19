package com.epam.cargo.infrastructure.web.binding.binder.impl;

import com.epam.cargo.infrastructure.context.ApplicationContext;
import com.epam.cargo.infrastructure.web.binding.binder.ParameterBinder;
import com.epam.cargo.infrastructure.web.data.sort.Sort;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Parameter;

import static com.epam.cargo.infrastructure.web.binding.constants.ConstantParameters.SORT_DIRECTION_PARAMETER;
import static com.epam.cargo.infrastructure.web.binding.constants.ConstantParameters.SORT_PROPERTY_PARAMETER;
import static com.epam.cargo.infrastructure.web.binding.utils.BindingUtils.bindSort;

/**
 * Designed for binding Sort object to controllers methods.
 * @author Roman Kovalchuk
 * @see Sort
 * @version 1.0
 * */
@SuppressWarnings("unused")
public class SortParameterBinder implements ParameterBinder {

    @Override
    public Object bindParameter(Parameter parameter, HttpServletRequest req, HttpServletResponse res, ApplicationContext context) {
        return bindSort(req, SORT_PROPERTY_PARAMETER.getName(), SORT_DIRECTION_PARAMETER.getName());
    }

    @Override
    public boolean matchesParameter(Parameter parameter) {
        return Sort.class.isAssignableFrom(parameter.getType());
    }
}