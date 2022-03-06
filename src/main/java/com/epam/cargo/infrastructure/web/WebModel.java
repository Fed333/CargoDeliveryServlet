package com.epam.cargo.infrastructure.web;

import java.util.HashMap;
import java.util.Map;

/**
 * Based on HashMap implementation of Model interface.
 * @since 06.03.2022
 * @see Model
 * @see HashMap
 * @author Roman Kovalchuk
 * */
@SuppressWarnings("unused")
public class WebModel implements Model {

    private final Map<String, Object> map = new HashMap<>();

    @Override
    public void addAttribute(String attribute, Object value) {
        map.put(attribute, value);
    }

    @Override
    public Object getAttribute(String attribute) {
        return map.get(attribute);
    }

    @Override
    public Model mergeAttributes(Map<String, Object> toMerge) {
        map.putAll(toMerge);
        return this;
    }

    @Override
    public boolean containsAttribute(String attribute) {
        return map.containsKey(attribute);
    }

    @Override
    public Map<String, Object> asMap() {
        return map;
    }
}
