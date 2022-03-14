package com.epam.cargo.infrastructure.web.binding.manager.impl;

import com.epam.cargo.infrastructure.annotation.Singleton;
import com.epam.cargo.infrastructure.web.binding.binder.ParameterBinder;
import com.epam.cargo.infrastructure.web.binding.manager.ParameterBinderManager;

import java.lang.reflect.Parameter;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static java.util.stream.Collectors.toList;

/**
 * Base implementation of ParameterBinderManager interface.
 * @author Roman Kovalchuk
 * @version 1.0
 * */
@Singleton
public class BaseParameterBinderManager implements ParameterBinderManager {

    private final List<ParameterBinder> binders = new ArrayList<>();

    @Override
    public void addParameterBinder(ParameterBinder binder) {
        Objects.requireNonNull(binder, "Null ParameterBinders aren't allowed!");
        binders.add(binder);
    }

    @Override
    public Optional<ParameterBinder> getParameterBinder(Parameter parameter) {
        List<ParameterBinder> found = binders.stream().filter(pb->pb.matchesParameter(parameter)).collect(toList());
        if (found.isEmpty()){
            return Optional.empty();
        }
        else if (found.size() > 1){
            throw new RuntimeException("More than 1 ParameterBinder was found for Parameter " + parameter);
        }
        return Optional.of(found.iterator().next());
    }
}
