package com.epam.cargo.infrastructure.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.epam.cargo.infrastructure.configurator.DispatcherCommandInterfaceObjectConfigurator;
import javax.servlet.http.HttpServletRequest;

/**
 * Marks controller dispatcher method's parameters to fetch injections of web parameters. <br/>
 * Applied only for parameters of controller's methods annotated with @RequestMapping annotation. <br/>
 * The annotation is managed with DispatcherCommandInterfaceObjectConfigurator.
 * @since 06.03.2022
 * @see Controller
 * @see RequestMapping
 * @see DispatcherCommandInterfaceObjectConfigurator
 * @author Roman Kovalchuk
 * */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.PARAMETER})
public @interface RequestAttribute {

    /**
     * Name of attribute in HttpServletRequest.
     * @see HttpServletRequest
     * */
    String name();

    /**
     * A defaultValue in case of attribute absence in HttpServletRequest.
     * @see HttpServletRequest
     * */
    String defaultValue();

}