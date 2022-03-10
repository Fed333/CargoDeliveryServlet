package com.epam.cargo.infrastructure.source.properties;

import com.epam.cargo.infrastructure.annotation.Singleton;

import java.io.IOException;
import java.util.Properties;

/**
 * Interface designed for obtaining properties resources.<br>
 * To run smoothly implementations should be annotated with @Singleton annotation.<br>
 * @see Singleton
 * @author Roman Kovalchuk
 * @version 1.0
 * */
public interface PropertiesSource {

    /**
     * Gives properties file by name.
     * @param name properties resource name
     * @return Properties object according to the resource name
     * @throws IOException in case of failing loading Properties
     * */
    Properties getProperties(String name) throws IOException;
}
