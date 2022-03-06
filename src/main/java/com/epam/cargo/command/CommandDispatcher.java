package com.epam.cargo.command;

import com.epam.cargo.infrastructure.annotation.FetchCommands;
import com.epam.cargo.infrastructure.annotation.Singleton;
import com.epam.cargo.infrastructure.dispatcher.Command;
import com.epam.cargo.infrastructure.dispatcher.DispatcherCommand;
import com.epam.cargo.infrastructure.dispatcher.HttpMethod;

import java.util.AbstractMap.SimpleImmutableEntry;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;

@Singleton
public final class CommandDispatcher implements DispatcherCommand {

    @FetchCommands
    private Map<SimpleImmutableEntry<String, HttpMethod>, AtomicReference<Command>> commands;

    @Override
    public Command getCommand(String url, HttpMethod method){
        return commands.get(new SimpleImmutableEntry<>(url, method)).get();
    }
}
