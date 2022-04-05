package com.epam.cargo.dao.test.infrastructure.application;

import com.epam.cargo.mock.connection.TestConnectionPool;
import com.epam.cargo.dao.connection.pool.ConnectionPool;
import org.fed333.servletboot.testing.source.TestInputStreamSource;
import org.fed333.servletboot.testing.source.TestPropertiesSource;
import org.fed333.servletboot.Application;
import org.fed333.servletboot.context.ApplicationContext;
import org.fed333.servletboot.source.input.InputStreamSource;
import org.fed333.servletboot.source.properties.PropertiesSource;

import java.util.HashMap;
import java.util.Map;

/**
 * Class designed special for raising test ApplicationContext.
 * @author Roman Kovalchuk
 * @version 1.0
 * */
public class TestApplication {

    /**
     * Builds ApplicationContext according to test demands.<br>
     * @return Properly configured special for test ApplicationContext
     * @see ApplicationContext
     * @since 1.0
     * */
    public static ApplicationContext run(){
        return Application.run(
                "com.epam.cargo",
                new HashMap<>(
                        Map.of(
                                ConnectionPool.class, TestConnectionPool.class,
                                InputStreamSource.class, TestInputStreamSource.class,
                                PropertiesSource.class, TestPropertiesSource.class
                        )
                )
        );
    }
}
