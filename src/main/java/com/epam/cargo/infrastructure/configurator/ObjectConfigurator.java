package com.epam.cargo.infrastructure.configurator;

import com.epam.cargo.infrastructure.context.ApplicationContext;
import com.epam.cargo.infrastructure.factory.ObjectFactory;

/**
 * Configurator interface of plain JavaBeans objects within the ApplicationContext.<br>
 * Configures the object in second stage of creating with ObjectFactory.
 * @see ObjectFactory
 * @see ApplicationContext
 * @author Roman Kovalchuk
 * @version 1.0
 * */
public interface ObjectConfigurator {
    /**
     * Properly configures plain JavaBean within the infrastructure.
     * @since 1.0
     * */
    void configure(Object o, ApplicationContext context);
}