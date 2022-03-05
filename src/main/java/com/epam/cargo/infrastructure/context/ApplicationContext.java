package com.epam.cargo.infrastructure.context;

import com.epam.cargo.infrastructure.annotation.Singleton;
import com.epam.cargo.infrastructure.config.Config;
import com.epam.cargo.infrastructure.factory.ObjectFactory;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class ApplicationContext {

    private ObjectFactory factory;

    private final Config config;

    private final Map<Class<?>, Object> cache = new ConcurrentHashMap<>();

    public ApplicationContext(Config config) {
        this.config = config;
    }

    public <T> T getObject(Class<T> clazz){

        if (cache.containsKey(clazz)){
            @SuppressWarnings("unchecked")
            T t = (T) cache.get(clazz);
            return t;
        }

        Class<? extends T> implClass = clazz;
        if (clazz.isInterface()){
            implClass = config.getImplClass(clazz);
        }

        T t = factory.createObject(implClass);

        if (implClass.isAnnotationPresent(Singleton.class)){
            cache.put(clazz, t);
        }

        return t;
    }

    public void setFactory(ObjectFactory factory) {
        this.factory = factory;
    }

    public Config getConfig() {
        return config;
    }
}
