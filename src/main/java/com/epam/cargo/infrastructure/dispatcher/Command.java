package com.epam.cargo.infrastructure.dispatcher;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * The Command interface to implement Command pattern for dispatching http requests in servlet application.<br/>
 * The mean of mapping http requests, executive part of DispatcherCommand.
 * @see DispatcherCommand
 * @author Roman Kovalchuk
 * @version 1.0
 * */
public interface Command {
    /**
     * Handle according http request.
     * @since 1.0
     * */
    void execute(HttpServletRequest req, HttpServletResponse res) throws IOException, ServletException;
}
