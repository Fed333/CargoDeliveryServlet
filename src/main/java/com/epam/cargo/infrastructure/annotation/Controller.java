package com.epam.cargo.infrastructure.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.epam.cargo.infrastructure.dispatcher.*;
import com.epam.cargo.infrastructure.configurator.DispatcherCommandInterfaceObjectConfigurator;

/**
 * Annotation to mark controller classes.
 * The part of infrastructure which dispatches commands to DispatcherCommand.<br/>
 * Annotated classes capable to assign methods with request mapping by means of @RequestMapping annotation.
 * Request handling methods provide simple infrastructure injections web parameters mechanism.
 * The method annotated with @RequestMapping is wrapped with Command method execute, and determine the view template by returned result.<br/>
 * Controllers are configured with DispatcherCommandInterfaceObjectConfigurator.
 * @see RequestMapping
 * @see DispatcherCommandInterfaceObjectConfigurator
 * @see Command
 * @see DispatcherCommand
 * @author Roman Kovalchuk
 * @version 1.0
 * */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
public @interface Controller {
}