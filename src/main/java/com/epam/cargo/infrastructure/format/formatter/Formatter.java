package com.epam.cargo.infrastructure.format.formatter;

/**
 * Designed for formatting types.
 * @author Roman Kovalchuk
 * @param <S> Source type where we format from
 * @param <T> Result target type which we format to
 * @author Roman Kovalchuk
 * @version 1.0
 * */
public interface Formatter<S, T> {
    T format(S source);
}