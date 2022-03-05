package com.epam.cargo.infrastructure.factory;

import com.epam.cargo.infrastructure.context.ApplicationContext;
import com.epam.cargo.infrastructure.configurator.ObjectConfigurator;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import javax.annotation.PostConstruct;

public class ObjectFactory {

    private final ApplicationContext context;

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
