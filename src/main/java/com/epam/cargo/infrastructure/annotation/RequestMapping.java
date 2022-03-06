package com.epam.cargo.infrastructure.annotation;

import com.epam.cargo.infrastructure.dispatcher.HttpMethod;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
public @interface RequestMapping {

    String prefix() default "/CargoDeliveryServlet";

    String url();

    HttpMethod method();
}