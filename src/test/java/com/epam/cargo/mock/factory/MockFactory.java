package com.epam.cargo.mock.factory;

import com.epam.cargo.infrastructure.annotation.Singleton;
import com.epam.cargo.infrastructure.config.Config;
import com.epam.cargo.mock.annotation.MockBean;
import com.epam.cargo.mock.configurator.InjectMockBeanConfigurator;
import com.epam.cargo.mock.configurator.MockObjectConfigurator;
import com.epam.cargo.mock.configurator.PropertyValueConfigurator;
import org.jetbrains.annotations.NotNull;
import org.mockito.Mockito;

import javax.annotation.PostConstruct;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;

/**
 * Designed for creation mock beans.<br>
 * @author Roman Kovalchuk
 * @version 1.0
 * */
public class MockFactory {

    private final List<MockObjectConfigurator> configurators = new ArrayList<>();

    private final Set<Class<?>> mocked = new HashSet<>();

    private final Map<Class<?>, Object> cache = new HashMap<>();

    private final Config config;

    public MockFactory(Class<?> testClass, Config config){
        for (Field field : testClass.getDeclaredFields()) {
            if (field.isAnnotationPresent(MockBean.class)){
                mocked.add(field.getType());
            }
        }
        configurators.addAll(List.of(new InjectMockBeanConfigurator(), new PropertyValueConfigurator()));
        this.config = config;
    }

    public <T> T getObject(Class<T> clazz){

        Class<? extends T> implClass = clazz;
        if (clazz.isInterface()){
            implClass = config.getImplClass(clazz);
        }

        if (cache.containsKey(implClass)){
            return (T) cache.get(implClass);
        }

        if (isMockedBean(clazz) || isMockedBean(implClass)){
            T mock = Mockito.mock(clazz);
            cache.put(implClass, mock);
            return mock;
        }

        try {
            T object;
            if (clazz.isAnnotationPresent(Singleton.class)){
                return getSingleton(implClass);
            }
            object = createObject(implClass);
            configureObject(object);
            invokeInit(object);
            return object;
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            e.printStackTrace();
            throw new RuntimeException(e.getCause());
        }
    }

    @NotNull
    private <T> T createObject(Class<T> clazz) throws InstantiationException, IllegalAccessException, InvocationTargetException, NoSuchMethodException {
        return clazz.getDeclaredConstructor().newInstance();
    }

    public boolean isMockedBean(Class<?> clazz){
        return mocked.contains(clazz);
    }

    private void configureObject(Object o){
        configurators.forEach(c -> c.configure(o, this));
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

    @SuppressWarnings("unchecked")
    private <T> T getSingleton(Class<T> clazz) throws InstantiationException, IllegalAccessException, InvocationTargetException, NoSuchMethodException {
        T object;
        if (cache.containsKey(clazz)){
            object = (T) cache.get(clazz);
        } else {
            object = createObject(clazz);
            cache.put(clazz, object);
            configureObject(object);
            invokeInit(object);
        }
        return object;
    }
}
