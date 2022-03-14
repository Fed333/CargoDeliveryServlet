package com.epam.cargo.infrastructure.configurator;

import com.epam.cargo.infrastructure.annotation.CommandMapping;
import com.epam.cargo.infrastructure.annotation.Controller;
import com.epam.cargo.infrastructure.annotation.FetchCommands;
import com.epam.cargo.infrastructure.annotation.RequestMapping;
import com.epam.cargo.infrastructure.context.ApplicationContext;
import com.epam.cargo.infrastructure.dispatcher.Command;
import com.epam.cargo.infrastructure.dispatcher.DispatcherCommand;
import com.epam.cargo.infrastructure.dispatcher.HttpMethod;
import com.epam.cargo.infrastructure.source.properties.PropertiesSource;
import com.epam.cargo.infrastructure.web.Model;
import com.epam.cargo.infrastructure.web.binding.binder.ParameterBinder;
import com.epam.cargo.infrastructure.web.binding.manager.ParameterBinderManager;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.AbstractMap.SimpleImmutableEntry;
import java.util.*;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

/**
 *  Configurator of request mapping within the infrastructure.
 *  Maintain http methods dispatching, using Command pattern.<br>
 *  This class fetches commands from annotated controllers methods and Command interface implementation to the DispatcherCommand.
 * @see Controller
 * @see RequestMapping
 * @see CommandMapping
 * @author Roman Kovalchuk
 * @version 1.3
 * */
@SuppressWarnings({"unused", "deprecation"})
public class DispatcherCommandInterfaceObjectConfigurator implements ObjectConfigurator {

    private static final String APPLICATION_PROPERTIES_PATH = "resources/application.properties";

    /**
     * Configures DispatcherCommand implementation object to maintain http request dispatching.
     * @since 1.0
     * */
    @Override
    public void configure(Object o, ApplicationContext context) {
        if (DispatcherCommand.class.isAssignableFrom(o.getClass())){
            Field field = getFetchCommandsField(o);

            Map<SimpleImmutableEntry<String, HttpMethod>, AtomicReference<Command>> commands = new HashMap<>();

            fetchCommandsFromCommandMappingAnnotation(context, commands);
            fetchCommandsFromRequestMappingAnnotation(context, commands);

            field.setAccessible(true);
            try {
                field.set(o, commands);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
                throw new RuntimeException("Failed to set a commands map value to the field " + field);
            }
        }
    }

    private void fetchCommandsFromRequestMappingAnnotation(ApplicationContext context, Map<SimpleImmutableEntry<String, HttpMethod>, AtomicReference<Command>> commands) {
        for (Class<?> controllerClass : context.getConfig().getScanner().getTypesAnnotatedWith(Controller.class)) {
            Object controller = context.getObject(controllerClass);
            for (final Method method : controllerClass.getDeclaredMethods()) {
                if (method.isAnnotationPresent(RequestMapping.class)){
                    RequestMapping annotation = method.getAnnotation(RequestMapping.class);

                    String prefix = "";
                    try {
                        Properties properties = context.getObject(PropertiesSource.class).getProperties(APPLICATION_PROPERTIES_PATH);
                        prefix = properties.getProperty("httpPrefix");
                    } catch (IOException e) {
                        e.printStackTrace();
                        //TODO log WARNING message
                    }

                    String mapping = prefix + annotation.url();
                    Command command = createCommand(controller, method, context);
                    assignCommand(commands, new SimpleImmutableEntry<>(mapping, annotation.method()), new AtomicReference<>(command));
                }
            }
        }
    }

    /**
     * Creates command to put in DispatcherCommand.<br>
     * Wraps method of controller to maintain web binding
     * and analyse the method's response with name of view template.<br>
     * @param controller object of controller class
     * @param method dispatching method by requested mapping
     * @param context infrastructure's ApplicationContext
     * @return Command lambda expression which represents Command interface implementation
     * to be added to DispatcherCommand
     * @since 1.0
     * @see Controller
     * @see ApplicationContext
     * @see Command
     * @author Roman Kovalchuk
     * */
    private Command createCommand(Object controller, Method method, ApplicationContext context) {
        return (req, res) -> {
            req.setAttribute(Model.class.getName(), context.getObject(Model.class));
            Parameter[] parameters = method.getParameters();
            Object[] arguments = new Object[parameters.length];

            for (int i = 0; i < parameters.length; i++) {
                Parameter parameter = parameters[i];
                Object argument = bindParameter(parameter, req, res, context);
                arguments[i] = argument;
            }
            method.setAccessible(true);

            String methodResponse;
            try {
                methodResponse = (String) method.invoke(controller, arguments);
            } catch (IllegalAccessException | InvocationTargetException e) {
                e.printStackTrace();
                throw new RuntimeException("Controller's method invocation failed " + method, e.getCause());
            }

            Model model = (Model)req.getAttribute(Model.class.getName());
            req.removeAttribute(Model.class.getName());
            model.asMap().forEach(req::setAttribute);
            String viewPrefix = (String)context.getObject(PropertiesSource.class).getProperties(APPLICATION_PROPERTIES_PATH).getOrDefault("viewPrefix", "");
            req.getRequestDispatcher(viewPrefix + methodResponse).forward(req, res);
        };
    }

    /**
     * Binds web parameter with ParameterBinderManager.<br>
     * @return value of bind parameter
     * @since 1.1
     * @author Roman Kovalchuk
     * */
    private Object bindParameter(Parameter parameter, HttpServletRequest req, HttpServletResponse res, ApplicationContext context) {
        Optional<ParameterBinder> parameterBinder = context.getObject(ParameterBinderManager.class).getParameterBinder(parameter);
        return parameterBinder.map(binder -> binder.bindParameter(parameter, req, res, context)).orElse(null);
    }

    private void fetchCommandsFromCommandMappingAnnotation(ApplicationContext context, Map<SimpleImmutableEntry<String, HttpMethod>, AtomicReference<Command>> commands) {
        for (Class<? extends Command> commandClass : context.getConfig().getScanner().getSubTypesOf(Command.class)) {
            CommandMapping commandMapping = commandClass.getAnnotation(CommandMapping.class);
            assignCommand(commands, new SimpleImmutableEntry<>(commandMapping.mapping(), commandMapping.method()), new AtomicReference<>(context.getObject(commandClass)));
        }
    }

    private void assignCommand(Map<SimpleImmutableEntry<String, HttpMethod>, AtomicReference<Command>> commands, SimpleImmutableEntry<String, HttpMethod> mapping, AtomicReference<Command> command){
        if (commands.containsKey(mapping)){
            throw new RuntimeException(String.format("Duplication Command mapping! Command for mapping [url: %s, method: %s] has been already assigned. ", mapping.getKey(), mapping.getValue()));
        }
        commands.put(mapping, command);
    }

    private Field getFetchCommandsField(Object o) {
        List<Field> fetchCommands = Arrays.stream(o.getClass().getDeclaredFields()).filter(field -> field.isAnnotationPresent(FetchCommands.class)).collect(Collectors.toList());
        if (fetchCommands.size() != 1){
            throw new RuntimeException("Class " + o.getClass() + " has 0 or more than 1 field with FetchCommands annotation");
        }
        Field field = fetchCommands.iterator().next();
        if (!Map.class.isAssignableFrom(field.getType())){
            throw new RuntimeException("Type of field " + field + " isn't a map.");
        }
        return field;
    }
}
