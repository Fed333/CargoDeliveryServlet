package com.epam.cargo.infrastructure.format.manager;

import com.epam.cargo.infrastructure.format.formatter.Formatter;

import java.util.List;
import java.util.Optional;

/**
 * Mean of managing formatters in ApplicationContext.<br>
 * To be up and running the implementations of Formatter interface must be only simple or anonymous classes.
 * No lambda expressions or method references are allowed.
 * @author Roman Kovalchuk
 * @version 1.0
 * */
public interface FormatterManager {

    /**
     * Adds Formatter to the storage,
     * if according formatter already exists changes an old formatter to the new one.
     * @param formatter Formatter to assign
     * @since 1.0
     * */
    void assignFormatter(Formatter<?,?> formatter);

    /**
     * Gives formatter according to the given types.
     * @param formatFromClass Type which we format from
     * @param formatToClass Type which we format to
     * @return Optional with Formatter if exists, otherwise an empty Optional
     * */
    <S,T> Optional<Formatter<S,T>> getFormatter(Class<S> formatFromClass, Class<T> formatToClass);

    /**
     * Gives all assigned formatters.
     * @return Copied list of all formatters, for content security inside FormatterManager
     * */
    List<Formatter<?,?>> getAllFormatters();
}