package com.epam.cargo.mock;

import com.epam.cargo.dao.connection.TestConnectionPool;
import com.epam.cargo.dao.connection.pool.ConnectionPool;
import com.epam.cargo.infrastructure.config.Config;
import com.epam.cargo.infrastructure.config.impl.JavaConfig;
import com.epam.cargo.infrastructure.source.input.InputStreamSource;
import com.epam.cargo.infrastructure.source.properties.PropertiesSource;
import com.epam.cargo.mock.factory.MockFactory;
import com.epam.cargo.mock.source.TestInputStreamSource;
import com.epam.cargo.mock.source.TestPropertiesSource;

import java.util.HashMap;
import java.util.Map;

public class MockApplication {

    public static MockFactory run(Class<?> testClass, String packageToScan){
        Config config = new JavaConfig(packageToScan, new HashMap<>(
                Map.of(
                        ConnectionPool.class, TestConnectionPool.class,
                        InputStreamSource.class, TestInputStreamSource.class,
                        PropertiesSource.class, TestPropertiesSource.class
                ))
        );
        return new MockFactory(testClass, config);
    }

}
