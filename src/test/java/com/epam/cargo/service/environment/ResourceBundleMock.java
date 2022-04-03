package com.epam.cargo.service.environment;

import org.mockito.MockedStatic;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Locale;
import java.util.PropertyResourceBundle;
import java.util.ResourceBundle;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mockStatic;

public class ResourceBundleMock {

    private MockedStatic<ResourceBundle> bundleMockedStatic;

    public void mockResourceBundle() throws IOException {

        bundleMockedStatic = mockStatic(ResourceBundle.class);
        bundleMockedStatic
                .when(()->ResourceBundle.getBundle(any()))
                .thenAnswer(invocationOnMock -> {
                    String baseName = invocationOnMock.getArgument(0, String.class);
                    FileInputStream fis = new FileInputStream("src/main/webapp/WEB-INF/classes" + baseName + ".properties");
                    return new PropertyResourceBundle(fis);
                });
        bundleMockedStatic
                .when(()->ResourceBundle.getBundle(any(), any(Locale.class)))
                .thenAnswer(invocationOnMock -> {
                    String baseName = invocationOnMock.getArgument(0, String.class);
                    FileInputStream fis = new FileInputStream("src/main/webapp/WEB-INF/classes" + baseName + ".properties");
                    return new PropertyResourceBundle(fis);
                });
    }

    public void closeMock(){
        bundleMockedStatic.close();
    }

}
