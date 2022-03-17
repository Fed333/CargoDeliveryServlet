package com.epam.cargo.controller;

import com.epam.cargo.infrastructure.annotation.Controller;
import com.epam.cargo.infrastructure.annotation.Inject;
import com.epam.cargo.infrastructure.annotation.RequestMapping;
import com.epam.cargo.infrastructure.dispatcher.HttpMethod;
import com.epam.cargo.service.AuthorizationService;

import javax.servlet.http.HttpSession;

@Controller
public class LogoutController {

    @Inject
    private AuthorizationService authorizationService;


    @RequestMapping(url = "/logout", method = HttpMethod.POST)
    public String logout(
        HttpSession session
    ){
        authorizationService.logout(session);
        return "login.jsp";
    }

}
