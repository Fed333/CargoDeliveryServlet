package com.epam.cargo.infrastructure.context;

import com.epam.cargo.infrastructure.annotation.Singleton;
import com.epam.cargo.infrastructure.config.Config;
import com.epam.cargo.infrastructure.factory.ObjectFactory;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Context within the infrastructure works.
 * @since 05.03.2022
 * @author Roman Kovalchuk
 * */
public class ApplicationContext {

    /**
     * Factory for creation and configuring plain JavaBeans objects within the infrastructure.
     * @see ObjectFactory
     * */
    private ObjectFactory factory;

    /**
     * Config for establishing implementation classes for interfaces.
     * @see Config
     * */
    private final Config config;

    /**
     * Cache of all plain JavaBeans singletons.<br>
     * Contains only objects annotated with @Singleton annotation
     * @see Singleton
     * */
    private final Map<Class<?>, Object> cache = new ConcurrentHashMap<>();

    public ApplicationContext(Config config) {
        this.config = config;
    }

    /**
     * Gives plain JavaBean object.<br>
     * Creates the object with ObjectFactory if it is absent in the cache.<br>
     * Caches singletons objects.
     * @param clazz Type of object being created
     * @return Created and configured plain JavaBean object within ApplicationContext
     * @see ObjectFactory
     * */
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

    /**
     * Gives Config with encapsulated logic of establishing implementation classes for interfaces.
     * @see Config
     * */
    public Config getConfig() {
        return config;
    }
}
