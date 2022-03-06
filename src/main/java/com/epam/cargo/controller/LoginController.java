package com.epam.cargo.controller;

import com.epam.cargo.infrastructure.annotation.Controller;
import com.epam.cargo.infrastructure.annotation.Inject;
import com.epam.cargo.infrastructure.annotation.RequestMapping;
import com.epam.cargo.infrastructure.dispatcher.HttpMethod;
import com.epam.cargo.infrastructure.web.Model;
import com.epam.cargo.service.CityService;

@Controller
public class LoginController {

    @Inject
    private CityService cityService;

    @RequestMapping(url = "/login", method = HttpMethod.GET)
    public String loginPage(
            Model model
    ){
        model.addAttribute("message", "message from LoginController.class");
        return "login.jsp";
    }

}
