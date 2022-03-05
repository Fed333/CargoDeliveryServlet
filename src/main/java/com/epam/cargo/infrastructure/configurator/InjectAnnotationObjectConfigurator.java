package com.epam.cargo.infrastructure.configurator;

import com.epam.cargo.infrastructure.ApplicationContext;
import com.epam.cargo.infrastructure.annotation.Inject;

import java.lang.reflect.Field;

public class InjectAnnotationObjectConfigurator implements ObjectConfigurator{

    @Override
    public void configure(Object o, ApplicationContext context) {
        for (Field field : o.getClass().getDeclaredFields()) {
            if (field.isAnnotationPresent(Inject.class)){
                field.setAccessible(true);
                try {
                    field.set(o, context.getObject(field.getType()));
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
