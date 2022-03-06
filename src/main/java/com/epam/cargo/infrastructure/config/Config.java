package com.epam.cargo.infrastructure.config;

import org.reflections.Reflections;

/**
 * Interface of Configuration for establishing implementation classes for interfaces.
 * @since 05.03.2022
 * @author Roman Kovalchuk
 * */
public interface Config {

    /**
     * Gives type which implements according interface
     * @param ifc interface to find its implementation
     * @return type which implement ifc interface
     * */
    <T> Class<? extends T> getImplClass(Class<T> ifc);

    /**
     * Provides object of class which extends opportunities of standard reflection API. <br>
     * Common usage for scanning java packages in finding implementations.
     * The Reflections class isn't available within standard JDK, for usage add appropriate dependency.
     *     <dependency>
     *       <groupId>org.reflections</groupId>
     *       <artifactId>reflections</artifactId>
     *       <version>0.9.12</version>
     *     </dependency>
     * @see Reflections
     * */
    Reflections getScanner();
}
