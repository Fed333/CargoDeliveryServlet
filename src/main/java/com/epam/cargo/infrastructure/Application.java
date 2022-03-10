package com.epam.cargo.infrastructure;

import com.epam.cargo.infrastructure.annotation.Singleton;
import com.epam.cargo.infrastructure.config.Config;
import com.epam.cargo.infrastructure.config.impl.JavaConfig;
import com.epam.cargo.infrastructure.context.ApplicationContext;
import com.epam.cargo.infrastructure.factory.ObjectFactory;
import com.epam.cargo.infrastructure.format.formatter.Formatter;
import com.epam.cargo.infrastructure.format.manager.FormatterManager;
import org.reflections.Reflections;

import java.lang.reflect.InvocationTargetException;
import java.util.Map;
import java.util.Set;

/**
 * Runner class of the infrastructure. <br>
 * Sets Config implementation, creates ObjectFactory, raises ApplicationContext.
 * @see Config
 * @see ObjectFactory
 * @see ApplicationContext
 * @author Roman Kovalchuk
 * @version 1.2
 * */
@SuppressWarnings("rawtypes")
public class Application {
    /**
     * Initialize the infrastructure according initial param settings.<br>
     * Raise ApplicationContext.
     * @param packageToScan work package
     * @param ifc2ImplClass configure map with interface to implementation class relation
     * @return raised ApplicationContext object
     * @since 1.1
     * @see ApplicationContext
     * */
    public static ApplicationContext run(String packageToScan, Map<Class, Class> ifc2ImplClass){
        Config config = new JavaConfig(packageToScan, ifc2ImplClass);
        ApplicationContext context = new ApplicationContext(config);
        ObjectFactory factory = new ObjectFactory(context);
        context.setFactory(factory);

        initNoLazySingletons(config, context);
        setSupportedFormatters(config, context);

        return context;
    }

    /**
     * Puts all singletons which are not lazy to the ApplicationContext right away after its creation and setup.<br>
     * @param config Config with interface to implementation classes
     * @param context ApplicationContext
     * @author Roman Kovalchuk
     * @since 1.1
     * */
    private static void initNoLazySingletons(Config config, ApplicationContext context) {
        for (Class<?> clazz : config.getScanner().getTypesAnnotatedWith(Singleton.class)) {
            if (clazz.getAnnotation(Singleton.class).type().equals(Singleton.Type.EAGER)){
                context.getObject(clazz);
            }
        }
    }

    /**
     * Sets all supported formatters.<br>
     * Adds FormatterManager with preliminary put basic Formatters to the ApplicationContext.<br>
     * @param config Config with interface to implementation classes
     * @param context ApplicationContext
     * @author Roman Kovalchuk
     * @since 1.2
     * */
    private static void setSupportedFormatters(Config config, ApplicationContext context){
        String packageToScan = Application.class.getPackageName() + ".format.formatter.impl";

        FormatterManager manager = context.getObject(FormatterManager.class);
        Reflections scanner = new Reflections(packageToScan);
        Set<Class<? extends Formatter>> formatters =  scanner.getSubTypesOf(Formatter.class);
        formatters.forEach(formatterClass ->{
            try {
                manager.assignFormatter(formatterClass.getDeclaredConstructor().newInstance());
            } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
                e.printStackTrace();
            }
        });
    }
}
