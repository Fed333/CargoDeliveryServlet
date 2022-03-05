package com.epam.cargo.infrastructure.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
public @interface Singleton {

    Type type() default Type.EAGER;

    enum Type {
        LAZY, EAGER
    }
}
