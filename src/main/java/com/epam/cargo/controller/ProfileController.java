package com.epam.cargo.controller;

import com.epam.cargo.entity.User;
import com.epam.cargo.infrastructure.annotation.Controller;
import com.epam.cargo.infrastructure.annotation.Inject;
import com.epam.cargo.infrastructure.annotation.RequestMapping;
import com.epam.cargo.infrastructure.dispatcher.HttpMethod;
import com.epam.cargo.infrastructure.web.Model;
import com.epam.cargo.service.AuthorizationService;
import com.epam.cargo.service.DeliveryApplicationService;
import com.epam.cargo.service.UserService;

import javax.servlet.http.HttpSession;

@Controller
public class ProfileController {

    @Inject
    private UserService userService;

    @Inject
    private AuthorizationService authorizationService;

    @Inject
    private DeliveryApplicationService applicationService;

    @RequestMapping(url = "/profile", method = HttpMethod.GET)
    public String profilePage(
            Model model,
            HttpSession session
    ){
        User authorized = authorizationService.getAuthorized(session);
        model.addAttribute("user", authorized);
        model.addAttribute("applications", applicationService.findAllByUserId(authorized.getId()));

        return "profile.jsp";
    }
}
