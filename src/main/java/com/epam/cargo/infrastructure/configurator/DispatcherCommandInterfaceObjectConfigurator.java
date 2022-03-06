package com.epam.cargo.infrastructure.configurator;

import com.epam.cargo.infrastructure.annotation.*;
import com.epam.cargo.infrastructure.context.ApplicationContext;
import com.epam.cargo.infrastructure.dispatcher.Command;
import com.epam.cargo.infrastructure.dispatcher.DispatcherCommand;
import com.epam.cargo.infrastructure.dispatcher.HttpMethod;
import com.epam.cargo.infrastructure.web.Model;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
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
 * @since 06.03.2022
 * @see Controller
 * @see RequestMapping
 * @see CommandMapping
 * @author Roman Kovalchuk
 * */
@SuppressWarnings({"unused", "deprecation"})
public class DispatcherCommandInterfaceObjectConfigurator implements ObjectConfigurator {

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
                    String mapping = annotation.prefix() + annotation.url();
                    Command command = createCommand(controller, method, context);
                    assignCommand(commands, new SimpleImmutableEntry<>(mapping, annotation.method()), new AtomicReference<>(command));
                }
            }
        }
    }

    private Command createCommand(Object controller, Method method, ApplicationContext context) {
        return (req, res) -> {
            req.setAttribute(Model.class.getName(), context.getObject(Model.class));
            Parameter[] parameters = method.getParameters();
            Object[] arguments = new Object[parameters.length];

            for (int i = 0; i < parameters.length; i++) {
                Parameter parameter = parameters[i];
                Object argument = fetchArgumentByParameter(parameter, req, res, context);
                arguments[i] = argument;
            }
            method.setAccessible(true);

            String methodResponse = null;
            try {
                methodResponse = (String) method.invoke(controller, arguments);

            } catch (IllegalAccessException | InvocationTargetException e) {
                e.printStackTrace();
                throw new RuntimeException("Failed to invoke controller's method " + method, e);
            }

            Model model = (Model)req.getAttribute(Model.class.getName());
            req.removeAttribute(Model.class.getName());
            model.asMap().forEach(req::setAttribute);
            req.getRequestDispatcher("WEB-INF/view/" + methodResponse).forward(req, res);
        };
    }

    private Object fetchArgumentByParameter(Parameter parameter, HttpServletRequest req, HttpServletResponse res, ApplicationContext context) {

        if (parameter.isAnnotationPresent(RequestAttribute.class)){
            RequestAttribute attributeAnnotation = parameter.getAnnotation(RequestAttribute.class);
            Object attributeValue = req.getAttribute(attributeAnnotation.name());
            if (Objects.isNull(attributeValue)){
                return attributeAnnotation.defaultValue();
            }
            return attributeValue;
        }

        if (ServletRequest.class.isAssignableFrom(parameter.getType())){
            return req;
        }
        if (ServletResponse.class.isAssignableFrom(parameter.getType())){
            return res;
        }
        if (HttpSession.class.isAssignableFrom(parameter.getType())){
            return req.getSession();
        }
        if (Model.class.isAssignableFrom(parameter.getType())){
            if (Objects.isNull(req.getAttribute(Model.class.getName()))){
                req.setAttribute(Model.class.getName(), context.getObject(Model.class));
            }
            return req.getAttribute(Model.class.getName());
        }
        return null;
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
