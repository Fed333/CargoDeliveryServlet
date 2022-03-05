package com.epam.cargo.infrastructure.config.impl;

import com.epam.cargo.infrastructure.config.Config;
import org.reflections.Reflections;

import java.util.Map;
import java.util.Set;

@SuppressWarnings({"unchecked", "rawtypes"})
public class JavaConfig implements Config {

    private final Reflections scanner;

    private final Map<Class, Class> ifc2ImplClass;

    public JavaConfig(String packageToScan, Map<Class, Class> ifc2ImplClass) {
        this.scanner = new Reflections(packageToScan);
        this.ifc2ImplClass = ifc2ImplClass;
    }

    @Override
    public <T> Class<? extends T> getImplClass(Class<T> ifc) {

        return ifc2ImplClass.computeIfAbsent(ifc, clazz -> {
            Set<Class<? extends T>> classes = scanner.getSubTypesOf(ifc);
            if (classes.size() != 1){
                throw new RuntimeException(ifc + " has 0 or more than 1 implementation was found! Please update your config.");
            }
            return classes.iterator().next();
        });
    }

    @Override
    public Reflections getScanner() {
        return scanner;
    }
}
