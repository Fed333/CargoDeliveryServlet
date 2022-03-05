package com.epam.cargo.infrastructure.configurator;

import com.epam.cargo.infrastructure.ApplicationContext;
import com.epam.cargo.infrastructure.annotation.PropertyValue;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.nio.file.Path;
import java.util.Properties;

public class PropertyValueAnnotationObjectConfigurator implements ObjectConfigurator{


    @Override
    public void configure(Object o, ApplicationContext context) {
        for (Field field : o.getClass().getDeclaredFields()) {
            if (field.isAnnotationPresent(PropertyValue.class)){
                PropertyValue annotation = field.getAnnotation(PropertyValue.class);
                Properties properties = new Properties();
                try {
                    FileInputStream fis = new FileInputStream("WEB-INF/web.xml");
//                    String path = ClassLoader.getSystemClassLoader().getResource(annotation.filePath()).getPath();
                    properties.load(getClass().getResourceAsStream("applications.properties"));
                } catch (IOException e){
                    e.printStackTrace();
                    throw new RuntimeException("Cannot set property for field " + field + ". File " + annotation.filePath() + " wasn't found or opened.");
                }
                String value = properties.getProperty(annotation.property());
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
