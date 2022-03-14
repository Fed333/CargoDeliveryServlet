package com.epam.cargo.infrastructure.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.epam.cargo.infrastructure.context.ApplicationContext;
import com.epam.cargo.infrastructure.configurator.PropertyValueAnnotationObjectConfigurator;

/**
 * Marks field to assign corresponding property value into.<br>
 * Fetch property from pointed properties file with further assignment.
 * Search value by pointed annotation property attribute, if it isn't specified uses field name instead.
 * Searching runs in properties file with specified filePath annotation attribute,
 * if filePath wasn't pointed, takes default "application.properties" file name.
 * The path to file is built from prefixPath + filePath. If prefixPath isn't specified, takes default "resources/"<br>
 * Applied only for String fields of objects which are part of ApplicationContext.<br>
 * The annotation is configured with PropertyValueAnnotationObjectConfigurator.
 * @see ApplicationContext
 * @see PropertyValueAnnotationObjectConfigurator
 * @author Roman Kovalchuk
 * @version 1.1
 * */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface PropertyValue {

    /**
     * Name of property in the properties file.
     * @since 1.0
     * */
    String property() default "";

    /**
     * Path to the properties file.
     * @since 1.0
     * */
    String filePath() default "application.properties";

    /**
     * Prefix path, repeatable part of file path to properties files.
     * @since 1.1
     * */
    String prefixPath() default "resources/";
}
