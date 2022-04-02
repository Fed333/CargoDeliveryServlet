package com.epam.cargo.dao.test.infrastructure.source;

import com.epam.cargo.infrastructure.annotation.Inject;
import com.epam.cargo.infrastructure.annotation.Singleton;
import com.epam.cargo.infrastructure.source.input.InputStreamSource;
import com.epam.cargo.infrastructure.source.properties.PropertiesSource;

import java.io.IOException;
import java.util.Properties;

/**
 * Test implementation of PropertiesSource infrastructure interface.<br>
 * Gets properties files with adding next prefix "src/main/"<br>
 * @see PropertiesSource
 * @see java.io.FileInputStream
 * @author Roman Kovalchuk
 * @version 1.0
 * */
@Singleton(type = Singleton.Type.LAZY)
public class TestPropertiesSource implements PropertiesSource {

    @Inject
    private InputStreamSource iss;

    @Override
    public Properties getProperties(String name) throws IOException {
        Properties properties = new Properties();
        properties.load(iss.getInputStream("src/test/"+name));
        return properties;
    }
}
