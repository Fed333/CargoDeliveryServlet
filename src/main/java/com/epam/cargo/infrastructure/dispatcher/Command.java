package com.epam.cargo.infrastructure.dispatcher;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * The Command interface to implement Command pattern for dispatching http requests in servlet application.<br/>
 * The mean of mapping http requests, executive part of DispatcherCommand.
 * @since  05.03.2022
 * @see DispatcherCommand
 * @author Roman Kovalchuk
 * */
public interface Command {
    /**
     * Handle according http request.
     * */
    void execute(HttpServletRequest req, HttpServletResponse res) throws IOException, ServletException;
}
