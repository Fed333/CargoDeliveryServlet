package com.epam.cargo.infrastructure.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.epam.cargo.infrastructure.context.ApplicationContext;
import com.epam.cargo.infrastructure.Application;

/**
 * Marks plain JavaBean classes as a part of ApplicationContext.<br/>
 * Annotated classes are managed like singletons,
 * has only one instance which is stored in special context cache.<br/>
 * Applied only for Java classes with no args constructor or default one. <br/>
 * The annotation is managed with ApplicationContext.
 * @since 05.03.2022
 * @see ApplicationContext
 * @author Roman Kovalchuk
 * */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
public @interface Singleton {

    /**
     * Gives fetch type of singleton.<br/>
     * */
    Type type() default Type.EAGER;

    /**
     * Fetch type of singleton. <br/>
     * EAGER Singletons get into ApplicationContext immediately after its creation.
     * EAGER fetching is managed with Application runner class<br/>
     * LAZY Singletons get into ApplicationContext only if they are needed (injection or getting the object).
     * @see Application
     * */
    enum Type {
        LAZY, EAGER
    }
}
