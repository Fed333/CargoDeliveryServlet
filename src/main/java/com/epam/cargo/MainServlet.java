package com.epam.cargo;


import com.epam.cargo.infrastructure.Application;
import com.epam.cargo.infrastructure.context.ApplicationContext;
import com.epam.cargo.infrastructure.dispatcher.HttpMethod;
import com.epam.cargo.infrastructure.dispatcher.impl.CommandDispatcher;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Entry launch point of the servlet application.<br>
 * The main application's servlet. Is configured via web.xml<br>
 * Sets the ApplicationContext, raises the infrastructure, assigns get,post methods handlers.<br>
 * @author Roman Kovalchuk
 * @see HttpServlet
 * @see ApplicationContext
 * @see Application
 * @version 1.2
 * */
public class MainServlet extends HttpServlet {

    private static final String APPLICATION_CONTEXT_ATTRIBUTE = "applicationContext";

    @Override
    public void init() {
        ServletContext servletContext = getServletContext();
        ApplicationContext applicationContext = Application.run("com.epam.cargo", new HashMap<>(Map.of()));
        servletContext.setAttribute(APPLICATION_CONTEXT_ATTRIBUTE, applicationContext);
    }

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
