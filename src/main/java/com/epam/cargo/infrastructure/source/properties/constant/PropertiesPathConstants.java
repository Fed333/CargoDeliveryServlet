package com.epam.cargo.infrastructure.source.properties.constant;

/**
 * Contains paths to all properties files.<br>
 * @author Roman Kovalchuk
 * @version 1.0
 * */
public enum PropertiesPathConstants {

    APPLICATION_PROPERTIES("resources/application.properties");

    /**
     * Path to properties file.<br>
     * @since 1.0
     * */
    private String path;

    PropertiesPathConstants(String path) {
        this.path = path;
    }

    public String getPath() {
        return path;
    }
}
