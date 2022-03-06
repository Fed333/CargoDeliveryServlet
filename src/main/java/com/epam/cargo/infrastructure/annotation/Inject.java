package com.epam.cargo.infrastructure.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.epam.cargo.infrastructure.context.ApplicationContext;
import com.epam.cargo.infrastructure.configurator.InjectAnnotationObjectConfigurator;

/**
 * Marks field of plain JavaBean class within ApplicationContext
 * to assign object of corresponding type for annotated field. <br/>
 * This annotation can be applied only on objects which are managed with ApplicationContext.<br/>
 * The injections are configured with InjectAnnotationObjectConfigurator
 * @since 05.03.2022
 * @see ApplicationContext
 * @see InjectAnnotationObjectConfigurator
 * @author Roman Kovalchuk
 * */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD})
public @interface Inject {
}
