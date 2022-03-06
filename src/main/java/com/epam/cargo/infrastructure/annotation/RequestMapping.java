package com.epam.cargo.infrastructure.annotation;

import com.epam.cargo.infrastructure.dispatcher.Command;
import com.epam.cargo.infrastructure.dispatcher.DispatcherCommand;
import com.epam.cargo.infrastructure.dispatcher.HttpMethod;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.epam.cargo.infrastructure.configurator.DispatcherCommandInterfaceObjectConfigurator;

/**
 * Annotation to dispatch http mapping in controller's methods<br/>
 * Uses on methods of classes annotated with @Controller annotation.<br/>
 * Brings information about mapping url and http method type to the annotated class.
 * Dispatching part of DispatcherCommand.<br/>
 * The annotation is managed with DispatcherCommandInterfaceObjectConfigurator.
 * @see Controller
 * @see DispatcherCommand
 * @see DispatcherCommandInterfaceObjectConfigurator
 * @author Roman Kovalchuk
 * @version 1.1
 * */

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
public @interface RequestMapping {

    /**
     * Prefix of http request mapping. Repeatable part of every http url.
     * @since 1.1
     * */
    String prefix() default "/CargoDeliveryServlet";

    /**
     * URL after prefix part.
     * @since 1.0
     * */
    String url();

    /**
     * Http method type.
     * @since 1.0
     * */
    HttpMethod method();
}