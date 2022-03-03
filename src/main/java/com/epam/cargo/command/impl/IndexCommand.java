package com.epam.cargo.command.impl;

import com.epam.cargo.command.Command;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class IndexCommand implements Command {

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse res) throws IOException, ServletException {
        req.getRequestDispatcher("WEB-INF/view/index.jsp").forward(req, res);
    }

}
