package com.epam.cargo.controller;

import com.epam.cargo.dto.AuthorizedDataRequest;
import com.epam.cargo.exception.WrongDataException;
import org.fed333.servletboot.annotation.Controller;
import org.fed333.servletboot.annotation.Inject;
import org.fed333.servletboot.annotation.RequestMapping;
import org.fed333.servletboot.dispatcher.HttpMethod;
import org.fed333.servletboot.web.Model;
import com.epam.cargo.service.AuthorizationService;

import javax.servlet.http.HttpSession;
import java.util.Objects;

import static com.epam.cargo.exception.ModelErrorAttribute.CREDENTIALS;
import static com.epam.cargo.exception.WrongInput.BAD_CREDENTIALS;

@Controller
public class LoginController {

    @Inject
    public AuthorizationService authorizationService;

    @RequestMapping(url = "/login", method = HttpMethod.GET)
    public String loginPage(
            HttpSession session
    ){
        if (Objects.nonNull(authorizationService.getAuthorized(session))){
            return "redirect:/forbidden";
        }
        return "login.jsp";
    }

    @RequestMapping(url = "/login", method = HttpMethod.POST)
    public String signIn(
            Model model,
            HttpSession session,
            AuthorizedDataRequest authorizedRequest
    ){
        if (Objects.nonNull(authorizationService.getAuthorized(session))){
            return "redirect:/forbidden";
        }
        try {
            if (authorizationService.login(authorizedRequest, session)){
                return "redirect:/profile";
            } else {
                model.addAttribute(CREDENTIALS.getAttr(), BAD_CREDENTIALS);
            }
        } catch (WrongDataException e){
            model.addAttribute(e.getModelAttribute(), e.getMessage());
        }
        return "login.jsp";
    }

}
