package com.epam.cargo.infrastructure.format.formatter.impl;

import com.epam.cargo.infrastructure.format.formatter.Formatter;

import java.util.Objects;

/**
 * Class for formatting String objects to Float.
 * @author Roman Kovalchuk
 * @version 1.0
 * */
public class StringToFloatFormatter implements Formatter<String, Float> {

    @Override
    public Float format(String source) {
        if (Objects.isNull(source) || source.isBlank()){
            return null;
        }
        return Float.parseFloat(source);
    }
}
