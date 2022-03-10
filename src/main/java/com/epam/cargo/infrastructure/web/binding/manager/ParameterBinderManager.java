package com.epam.cargo.infrastructure.web.binding.manager;

import com.epam.cargo.infrastructure.web.binding.binder.ParameterBinder;

import java.lang.reflect.Parameter;
import java.util.Optional;

/**
 * Designed for managing ParameterBinder objects.<br>
 * @author Roman Kovalchuk
 * @version 1.0
 * */
public interface ParameterBinderManager {

    /**
     * Adds new ParameterBinder to the manager.<br>
     * @param binder ParameterBinder to add
     * */
    void addParameterBinder(ParameterBinder binder);

    /**
     * Finds according ParameterBinder which matches with given Parameter.<br>
     * In case of existing multiple binders matched by Parameter, throws RuntimeException
     * @param parameter search criterion of binder
     * @return Optional with ParameterBinder inside, ot empty Optional if missing
     * @throws RuntimeException if by parameter more than 1 binder was found
     * */
    Optional<ParameterBinder> getParameterBinder(Parameter parameter);

}
