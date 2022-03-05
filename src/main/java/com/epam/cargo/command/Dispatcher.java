package com.epam.cargo.command;

import com.epam.cargo.infrastructure.annotation.FetchCommands;
import com.epam.cargo.infrastructure.annotation.Singleton;
import com.epam.cargo.infrastructure.dispatcher.Command;
import com.epam.cargo.infrastructure.dispatcher.DispatcherCommand;

import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;

@Singleton
public final class Dispatcher implements DispatcherCommand {

    @FetchCommands
    private Map<String, AtomicReference<Command>> commands;

    @Override
    public Command getCommand(String url){
        return commands.get(url).get();
    }
}
