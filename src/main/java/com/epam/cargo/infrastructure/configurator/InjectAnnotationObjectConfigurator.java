package com.epam.cargo.infrastructure.configurator;

import com.epam.cargo.infrastructure.annotation.Inject;
import com.epam.cargo.infrastructure.context.ApplicationContext;

import java.lang.reflect.Field;

/**
 * Configurator of injecting plain JavaBean to the field annotated with @Inject annotation.
 * Gets plain JavaBean from the ApplicationContext and sets it to the field.
 * @since 05.03.2022
 * @see Inject
 * @see ApplicationContext
 * @author Roman Kovalchuk
 * */
@SuppressWarnings("unused")
public class InjectAnnotationObjectConfigurator implements ObjectConfigurator{

    /**
     * Injects plain JavaBean object by type of annotated field from ApplicationContext.
     * @see ApplicationContext
     * */
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
