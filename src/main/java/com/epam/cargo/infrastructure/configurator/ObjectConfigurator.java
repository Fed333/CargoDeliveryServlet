package com.epam.cargo.infrastructure.configurator;

import com.epam.cargo.infrastructure.ApplicationContext;

public interface ObjectConfigurator {
    void configure(Object o, ApplicationContext context);
}