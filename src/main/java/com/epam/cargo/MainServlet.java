package com.epam.cargo;


import org.fed333.servletboot.context.ApplicationContext;
import org.fed333.servletboot.dispatcher.HttpMethod;
import org.fed333.servletboot.dispatcher.impl.CommandDispatcher;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.epam.cargo.listener.ContextListener.APPLICATION_CONTEXT_ATTRIBUTE;

/**
 * The main application's servlet. Is configured via web.xml<br>
 * @author Roman Kovalchuk
 * @see HttpServlet
 * @version 1.2
 * */
public class MainServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ApplicationContext context = (ApplicationContext) getServletContext().getAttribute(APPLICATION_CONTEXT_ATTRIBUTE);
        String requestURI = req.getRequestURI();
        context.getObject(CommandDispatcher.class).getCommand(requestURI, HttpMethod.GET).execute(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ApplicationContext context = (ApplicationContext) getServletContext().getAttribute(APPLICATION_CONTEXT_ATTRIBUTE);
        String requestURI = req.getRequestURI();
        context.getObject(CommandDispatcher.class).getCommand(requestURI, HttpMethod.POST).execute(req, resp);

    }

}
