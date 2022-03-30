package com.epam.cargo.listener;

import com.epam.cargo.infrastructure.Application;
import com.epam.cargo.infrastructure.context.ApplicationContext;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import java.util.HashMap;
import java.util.Map;

/**
 * Entry launch point of the servlet application.<br>
 * Sets the ApplicationContext, raises the infrastructure, assigns get,post methods handlers.<br>
 * @author Roman Kovalchuk
 * @see ApplicationContext
 * @see Application
 * @version 1.0
 * */
@WebListener
public class ContextListener implements ServletContextListener {

    public static final String APPLICATION_CONTEXT_ATTRIBUTE = "applicationContext";

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        ServletContext servletContext = sce.getServletContext();
        ApplicationContext applicationContext = Application.run("com.epam.cargo", new HashMap<>(Map.of()));
        servletContext.setAttribute(APPLICATION_CONTEXT_ATTRIBUTE, applicationContext);
    }
}
