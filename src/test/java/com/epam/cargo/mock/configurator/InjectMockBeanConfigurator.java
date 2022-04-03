package com.epam.cargo.mock.configurator;

import com.epam.cargo.infrastructure.annotation.Inject;
import com.epam.cargo.mock.factory.MockFactory;

import java.lang.reflect.Field;

/**
 * Designed for injecting mock beans.<br>
 * Manage {@link Inject} annotation via {@link MockFactory} in test context.
 * @author Roman Kovalchuk
 * @version 1.0
 **/
public class InjectMockBeanConfigurator implements MockObjectConfigurator {

    @Override
    public void configure(Object o, MockFactory factory) {
        for (Field field : o.getClass().getDeclaredFields()) {
            if (field.isAnnotationPresent(Inject.class)){
                Class<?> fieldClass = field.getType();
                Object injectedValue = factory.getObject(fieldClass);

                field.setAccessible(true);
                try {
                    field.set(o, injectedValue);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                    throw new RuntimeException(e.getCause());
                }
            }
        }
    }
}
