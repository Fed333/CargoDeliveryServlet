package com.epam.cargo.infrastructure.annotation;

import com.epam.cargo.infrastructure.dispatcher.HttpMethod;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.epam.cargo.infrastructure.dispatcher.*;

/**
 * Annotation to mark dispatch mapping of single Command implementation's classes.<br>
 * Uses on implemented Command interface classes.<br>
 * Brings information about mapping url and http method type to the annotated class.
 * Dispatching part of DispatcherCommand.
 *
 * @deprecated
 * @see Command
 * @see DispatcherCommand
 * @author Roman Kovalchuk
 * @version 1.0
 * */

@Deprecated
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
public @interface CommandMapping {

    /**
     * URL mapping path.
     * @since 1.0
     * */
    String mapping();

    /**
     * Http method type.
     * @since 1.0
     * */
    HttpMethod method();
}
