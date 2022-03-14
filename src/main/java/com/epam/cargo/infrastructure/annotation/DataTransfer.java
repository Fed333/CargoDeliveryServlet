package com.epam.cargo.infrastructure.annotation;

import com.epam.cargo.infrastructure.configurator.DispatcherCommandInterfaceObjectConfigurator;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Annotation to mark controller method's params like web DataTransferObject.<br>
 * Uses in dispatching methods of controller which are annotated with @RequestMapping annotation.
 * The annotation is managed with DispatcherCommandInterfaceObjectConfigurator.
 * @author Roman Kovalchuk
 * @see Controller
 * @see RequestMapping
 * @see DispatcherCommandInterfaceObjectConfigurator
 * @version 1.0
 * */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.PARAMETER)
public @interface DataTransfer {
}
