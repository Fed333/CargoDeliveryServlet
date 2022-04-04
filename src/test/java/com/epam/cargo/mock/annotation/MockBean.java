package com.epam.cargo.mock.annotation;

import com.epam.cargo.mock.factory.MockFactory;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Annotation to mark classes which instances should {@link MockFactory} mock.
 * @author Roman Kovalchuk
 * @version 1.0
 * */
@Retention(RetentionPolicy.RUNTIME)
public @interface MockBean {
}