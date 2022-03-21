package com.epam.cargo.controller;

import com.epam.cargo.dto.AuthorizedDataRequest;
import com.epam.cargo.infrastructure.annotation.Controller;
import com.epam.cargo.infrastructure.annotation.Inject;
import com.epam.cargo.infrastructure.annotation.RequestMapping;
import com.epam.cargo.infrastructure.dispatcher.HttpMethod;
import com.epam.cargo.infrastructure.web.Model;
import com.epam.cargo.service.AuthorizationService;

import javax.servlet.http.HttpSession;

@Controller
public class LoginController {

    @Inject
    public AuthorizationService authorizationService;

    @RequestMapping(url = "/login", method = HttpMethod.GET)
    public String loginPage(
            Model model
    ){
        return "login.jsp";
    }

    @RequestMapping(url = "/login", method = HttpMethod.POST)
    public String signIn(
            HttpSession session,
            AuthorizedDataRequest authorizedRequest
    ){
        if (authorizationService.login(authorizedRequest, session)){
            return "redirect:/profile";
        }
        return "login.jsp";
    }

}
