package com.epam.cargo.infrastructure.source.properties.constant;

/**
 * Contains all keys of application.properties file.<br>
 * @author Roman Kovalchuk
 * @version 1.0
 * */
public enum ApplicationPropertiesConstants {

    VIEW_PREFIX("viewPrefix"),
    HTTP_PREFIX("httpPrefix");

    /**
     * Property key.
     * @since 1.0
     * */
    private String key;

    ApplicationPropertiesConstants(String key) {
        this.key = key;
    }

    public String getKey() {
        return key;
    }
}
