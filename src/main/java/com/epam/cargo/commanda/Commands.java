package com.epam.cargo.commanda;

import com.epam.cargo.commanda.impl.IndexCommand;
import com.epam.cargo.commanda.impl.LoginCommand;

import java.util.HashMap;
import java.util.Map;


public final class Commands {
    private static final Map<String, Command> commands = new HashMap<>();

    static {
        commands.put("/", new IndexCommand());
        commands.put("/CargoDeliveryServlet/login", new LoginCommand());
    }

    public static Command getCommand(String url){
        return commands.get(url);
    }
}
