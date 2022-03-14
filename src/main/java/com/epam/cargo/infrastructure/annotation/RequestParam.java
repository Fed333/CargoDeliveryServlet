package com.epam.cargo.infrastructure.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.epam.cargo.infrastructure.configurator.DispatcherCommandInterfaceObjectConfigurator;
import javax.servlet.http.HttpServletRequest;

/**
 * Marks controller dispatcher method's parameters to fetch injections of web parameters. <br>
 * Applied only for parameters of controller's methods annotated with @RequestMapping annotation. <br>
 * The annotation is managed with DispatcherCommandInterfaceObjectConfigurator.
 * @see Controller
 * @see RequestMapping
 * @see DispatcherCommandInterfaceObjectConfigurator
 * @author Roman Kovalchuk
 * @version 1.0
 * */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.PARAMETER})
public @interface RequestParam {

    /**
     * Name of attribute in HttpServletRequest.
     * @since 1.0
     * @see HttpServletRequest
     * */
    String name();

    /**
     * A defaultValue in case of attribute absence in HttpServletRequest.
     * @since 1.0
     * @see HttpServletRequest
     * */
    String defaultValue();

}