package com.epam.cargo.command;

import com.epam.cargo.command.impl.IndexCommand;
import com.epam.cargo.command.impl.LoginCommand;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;


public final class Commands {
    private static final Map<String, AtomicReference<Command>> commands = new HashMap<>();

    static {
        commands.put("/CargoDeliveryServlet/", new AtomicReference<Command>(new IndexCommand()));
        commands.put("/CargoDeliveryServlet/login", new AtomicReference<Command>(new LoginCommand()));
    }

    public static Command getCommand(String url){
        return commands.get(url).get();
    }
}
