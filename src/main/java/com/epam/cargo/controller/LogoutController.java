package com.epam.cargo.controller;

import org.fed333.servletboot.annotation.Controller;
import org.fed333.servletboot.annotation.Inject;
import org.fed333.servletboot.annotation.RequestMapping;
import org.fed333.servletboot.dispatcher.HttpMethod;
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
