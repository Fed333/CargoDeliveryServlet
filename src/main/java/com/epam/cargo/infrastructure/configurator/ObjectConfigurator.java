package com.epam.cargo.infrastructure.configurator;

import com.epam.cargo.infrastructure.context.ApplicationContext;

public interface ObjectConfigurator {
    void configure(Object o, ApplicationContext context);
}