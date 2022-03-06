package com.epam.cargo.infrastructure.factory;

import com.epam.cargo.infrastructure.context.ApplicationContext;
import com.epam.cargo.infrastructure.configurator.ObjectConfigurator;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import javax.annotation.PostConstruct;

/**
 * ObjectFactory class, for creating plain JavaBeans objects within ApplicationContext
 *
 * @since  05.03.2022
 * @author Roman Kovalchuk
 * */

public class ObjectFactory {

    /**
     * Context within the infrastructure works.
     * @see ApplicationContext
     * */
    private final ApplicationContext context;

    /**
     * List with ObjectConfigurators to configure plain JavaBean objects, after its creation
     * @see ObjectConfigurator
     * */
    private final List<ObjectConfigurator> configurators = new CopyOnWriteArrayList<>();

    public ObjectFactory(ApplicationContext context) {
        this.context = context;

        for(Class<? extends ObjectConfigurator> clazz : context.getConfig().getScanner().getSubTypesOf(ObjectConfigurator.class)){
            try {
                configurators.add(clazz.getDeclaredConstructor().newInstance());
            } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Creates object according to the given class,
     * the class must have constructor without arguments or default one
     * @param clazz Type of object being created
     * @throws RuntimeException when object creating was failed
     * @return Created and configured plain JavaBean object within ApplicationContext
     * */
    public <T> T createObject(Class<T> clazz){

        try {
            T object = create(clazz);
            configureObject(object);
            invokeInit(object);
            return object;

        } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to create object with factory!", e);
        }
    }

    private <T> T create(Class<T> clazz) throws InstantiationException, IllegalAccessException, InvocationTargetException, NoSuchMethodException {
        return clazz.getDeclaredConstructor().newInstance();
    }

    private <T> void invokeInit(T object) {
        Class<?> implClass = object.getClass();
        for (Method method : implClass.getDeclaredMethods()) {
            if (method.isAnnotationPresent(PostConstruct.class)){
                method.setAccessible(true);
                try {
                    method.invoke(object);
                } catch (IllegalAccessException | InvocationTargetException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private <T> void configureObject(T object) {
        configurators.forEach(configurator->configurator.configure(object, context));
    }
}
