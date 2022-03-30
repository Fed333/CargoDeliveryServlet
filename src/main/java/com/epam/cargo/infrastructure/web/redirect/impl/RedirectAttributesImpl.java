package com.epam.cargo.infrastructure.web.redirect.impl;

import com.epam.cargo.infrastructure.web.redirect.RedirectAttributes;

import java.util.HashMap;
import java.util.Map;

/**
 * Base implementation of {@link RedirectAttributes} interface.<br>
 * @version 1.0
 * @author Roman Kovalchuk
 * */
public class RedirectAttributesImpl implements RedirectAttributes {

    private final Map<String, Object> attributes = new HashMap<>();

    @Override
    public RedirectAttributes addFlashAttribute(String attribute, Object value) {
        attributes.put(attribute, value);
        return this;
    }

    @Override
    public Map<String, Object> getFlashAttributes() {
        return attributes;
    }
}
