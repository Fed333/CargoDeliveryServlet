package com.epam.cargo.infrastructure;

import com.epam.cargo.infrastructure.annotation.CommandMapping;
import com.epam.cargo.infrastructure.context.ApplicationContext;
import com.epam.cargo.infrastructure.annotation.Singleton;
import com.epam.cargo.infrastructure.config.Config;
import com.epam.cargo.infrastructure.config.impl.JavaConfig;
import com.epam.cargo.infrastructure.factory.ObjectFactory;

import java.util.Map;

@SuppressWarnings("rawtypes")
public class Application {
    public static ApplicationContext run(String packageToScan, Map<Class, Class> ifc2ImplClass){
        Config config = new JavaConfig(packageToScan, ifc2ImplClass);
        ApplicationContext context = new ApplicationContext(config);
        ObjectFactory factory = new ObjectFactory(context);
        context.setFactory(factory);

        initNoLazySingletons(config, context);

        return context;
    }

    private static void initNoLazySingletons(Config config, ApplicationContext context) {
        for (Class<?> clazz : config.getScanner().getTypesAnnotatedWith(Singleton.class)) {
            if (clazz.getAnnotation(Singleton.class).type().equals(Singleton.Type.EAGER)){
                context.getObject(clazz);
            }
        }
    }
}
