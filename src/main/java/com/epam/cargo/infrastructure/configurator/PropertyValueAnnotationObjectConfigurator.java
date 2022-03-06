package com.epam.cargo.infrastructure.configurator;

import com.epam.cargo.infrastructure.context.ApplicationContext;
import com.epam.cargo.infrastructure.annotation.PropertyValue;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.Properties;

/**
 * Configurator of setting property value to the field annotated with @PropertyValue annotation.
 * Gets property value from pointed in annotation property file.
 * @since 05.03.2022
 * @see PropertyValue
 * @author Roman Kovalchuk
 * */
@SuppressWarnings("unused")
public class PropertyValueAnnotationObjectConfigurator implements ObjectConfigurator{

    @Override
    public void configure(Object o, ApplicationContext context) {
        for (Field field : o.getClass().getDeclaredFields()) {
            if (field.isAnnotationPresent(PropertyValue.class)){
                PropertyValue annotation = field.getAnnotation(PropertyValue.class);
                Properties properties = new Properties();
                try {
                    getClass().getClassLoader().getResourceAsStream(annotation.filePath());
                    properties.load(getClass().getClassLoader().getResourceAsStream(annotation.filePath()));
                } catch (IOException e){
                    e.printStackTrace();
                    throw new RuntimeException("Cannot set property for field " + field + ". File " + annotation.filePath() + " wasn't found or opened.");
                }
                String value = properties.getProperty(!annotation.property().isBlank() ? annotation.property() : field.getName());
                field.setAccessible(true);
                try {
                    field.set(o, value);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                    throw new RuntimeException("Cannot set value " + value + " to the field " + field, e);
                }
            }
        }
    }
}
