package com.epam.cargo.command.impl;

import com.epam.cargo.infrastructure.annotation.Singleton;
import com.epam.cargo.infrastructure.dispatcher.Command;
import com.epam.cargo.infrastructure.annotation.CommandMapping;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Singleton
@CommandMapping(mapping = "/CargoDeliveryServlet/login")
public class LoginCommand implements Command {

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse res) throws IOException, ServletException {
        req.getRequestDispatcher("WEB-INF/view/login.jsp").forward(req, res);
    }
}
