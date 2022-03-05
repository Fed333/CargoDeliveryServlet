package com.epam.cargo.infrastructure.configurator;

import com.epam.cargo.infrastructure.dispatcher.Command;
import com.epam.cargo.infrastructure.dispatcher.DispatcherCommand;
import com.epam.cargo.infrastructure.annotation.CommandMapping;
import com.epam.cargo.infrastructure.annotation.FetchCommands;
import com.epam.cargo.infrastructure.context.ApplicationContext;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

public class DispatcherCommandInterfaceObjectConfigurator implements ObjectConfigurator {
    @Override
    public void configure(Object o, ApplicationContext context) {
        if (DispatcherCommand.class.isAssignableFrom(o.getClass())){
            List<Field> fetchCommands = Arrays.stream(o.getClass().getDeclaredFields()).filter(field -> field.isAnnotationPresent(FetchCommands.class)).collect(Collectors.toList());
            if (fetchCommands.size() != 1){
                throw new RuntimeException("Class " + o.getClass() + " has 0 or more than 1 field with FetchCommands annotation");
            }
            Field field = fetchCommands.iterator().next();
            if (!Map.class.isAssignableFrom(field.getType())){
                throw new RuntimeException("Type of field " + field + " isn't a map.");
            }
            Map<String, AtomicReference<Command>> commands = new HashMap<>();
            for (Class<? extends Command> commandClass : context.getConfig().getScanner().getSubTypesOf(Command.class)) {
                CommandMapping commandMapping = commandClass.getAnnotation(CommandMapping.class);
                commands.put(commandMapping.mapping(), new AtomicReference<>(context.getObject(commandClass)));
            };
            field.setAccessible(true);
            try {
                field.set(o, commands);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
                throw new RuntimeException("Failed to set a commands map value to the field " + field);
            }
        }
    }
}
