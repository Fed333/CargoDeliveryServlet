package com.epam.cargo.command.impl;

import com.epam.cargo.infrastructure.annotation.Singleton;
import com.epam.cargo.infrastructure.dispatcher.Command;
import com.epam.cargo.infrastructure.annotation.CommandMapping;
import com.epam.cargo.infrastructure.annotation.Inject;
import com.epam.cargo.service.CityService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Singleton
@CommandMapping(mapping = "/CargoDeliveryServlet/")
public class IndexCommand implements Command {

    @Inject
    private CityService cityService;

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse res) throws IOException, ServletException {
        req.setAttribute("cities", cityService.findAll());
        req.getRequestDispatcher("WEB-INF/view/index.jsp").forward(req, res);
    }

}
