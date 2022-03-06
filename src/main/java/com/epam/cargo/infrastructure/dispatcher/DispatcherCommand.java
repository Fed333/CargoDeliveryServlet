package com.epam.cargo.infrastructure.dispatcher;

public interface DispatcherCommand {
    Command getCommand(String url, HttpMethod method);
}
