package com.epam.cargo.mock.configurator;

import com.epam.cargo.mock.factory.MockFactory;

public interface MockObjectConfigurator {

    void configure(Object o, MockFactory factory);
}
