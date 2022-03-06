package com.epam.cargo.infrastructure.annotation;

import com.epam.cargo.infrastructure.dispatcher.HttpMethod;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Deprecated
@Retention(RetentionPolicy.RUNTIME)
public @interface CommandMapping {

    String mapping();

    HttpMethod method();
}
