package com.epam.cargo.infrastructure.web;

import java.util.Map;

public interface Model {

    void addAttribute(String attribute, Object value);

    Object getAttribute(String attribute);

    Model mergeAttributes(Map<String, Object> toMerge);

    boolean containsAttribute(String attribute);

    Map<String, Object> asMap();
}
