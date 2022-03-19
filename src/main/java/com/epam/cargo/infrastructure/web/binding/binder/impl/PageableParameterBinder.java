package com.epam.cargo.infrastructure.web.binding.binder.impl;

import com.epam.cargo.infrastructure.annotation.PageableDefault;
import com.epam.cargo.infrastructure.context.ApplicationContext;
import com.epam.cargo.infrastructure.web.binding.binder.ParameterBinder;
import com.epam.cargo.infrastructure.web.binding.utils.BindingUtils;
import com.epam.cargo.infrastructure.web.data.pageable.Pageable;
import com.epam.cargo.infrastructure.web.data.pageable.impl.PageRequest;
import com.epam.cargo.infrastructure.web.data.sort.Sort;
import org.jetbrains.annotations.NotNull;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Parameter;
import java.util.Objects;
import java.util.function.Supplier;

import static com.epam.cargo.infrastructure.web.binding.constants.ConstantParameters.*;

/**
 * Designed for binding Pageable object to controllers methods.
 * @author Roman Kovalchuk
 * @see Pageable
 * @version 1.0
 * */
@SuppressWarnings("unused")
public class PageableParameterBinder implements ParameterBinder {

    private static final Integer DEFAULT_PAGE_NUMBER = 0;
    private static final int DEFAULT_PAGE_SIZE = 10;

    @Override
    public Object bindParameter(Parameter parameter, HttpServletRequest req, HttpServletResponse res, ApplicationContext context) {
        int page = bindPage(parameter, PAGE_PARAMETER.getName(), req);
        int size = bindSize(parameter, SIZE_PARAMETER.getName(), req);
        Sort sort = bindSort(parameter, SORT_PROPERTY_PARAMETER.getName(), SORT_DIRECTION_PARAMETER.getName(), req);
        return new PageRequest(page, size, sort);
    }

    @NotNull
    private Sort bindSort(Parameter parameter, String propertyParameter, String directionParameter, HttpServletRequest req) {
        Sort sort = BindingUtils.bindSort(req, propertyParameter, directionParameter);
        if (sort.getOrders().isEmpty()){
            if (parameter.isAnnotationPresent(PageableDefault.class)) {
                PageableDefault pageableDefault = parameter.getAnnotation(PageableDefault.class);
                sort = Sort.by(BindingUtils.bindOrders(pageableDefault.sort(), pageableDefault.directions()));
            }
            else {
                sort = Sort.by();
            }
        }
        return sort;
    }

    @Override
    public boolean matchesParameter(Parameter parameter) {
        return Pageable.class.isAssignableFrom(parameter.getType());
    }

    @NotNull
    private Integer bindPage(Parameter parameter, String parameterName, HttpServletRequest req) {
        return bindInt(parameterName, req, () -> defaultPage(parameter));
    }

    @NotNull
    private Integer bindSize(Parameter parameter, String parameterName, HttpServletRequest req) {
        return bindInt(parameterName, req, () -> defaultSize(parameter));
    }

    private int defaultSize(Parameter parameter) {
        if (parameter.isAnnotationPresent(PageableDefault.class)){
            return parameter.getAnnotation(PageableDefault.class).size();
        } else {
            return DEFAULT_PAGE_SIZE;
        }
    }

    private int defaultPage(Parameter parameter) {
        if (parameter.isAnnotationPresent(PageableDefault.class)){
            return parameter.getAnnotation(PageableDefault.class).page();
        } else {
            return DEFAULT_PAGE_NUMBER;
        }
    }

    private Integer bindInt(String parameterName, HttpServletRequest req, Supplier<Integer> defaultValue){
        String value = req.getParameter(parameterName);
        if (Objects.nonNull(value) && !value.isBlank()){
            return Integer.parseInt(value);
        } else {
            return defaultValue.get();
        }
    }
}