package com.epam.cargo.mock;

import com.epam.cargo.dao.connection.pool.ConnectionPool;
import com.epam.cargo.mock.connection.TestConnectionPool;
import org.fed333.servletboot.context.ApplicationContext;
import org.fed333.servletboot.testing.TestApplication;
import org.fed333.servletboot.testing.context.MockApplicationContext;

import java.util.Map;


/**
 * Class designed special for raising test mocked ApplicationContext.
 * @author Roman Kovalchuk
 * @see MockApplicationContext
 * @version 1.0
 * */
public class MockApplication {

    public static MockApplicationContext run(Class<?> testClass, String packageToScan){
        return TestApplication.run(testClass, packageToScan, Map.of(
                ConnectionPool.class, TestConnectionPool.class
        ));
    }

}
