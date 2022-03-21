package com.epam.cargo.controller;

import com.epam.cargo.entity.DeliveryApplication;
import com.epam.cargo.entity.User;
import com.epam.cargo.infrastructure.annotation.*;
import com.epam.cargo.infrastructure.dispatcher.HttpMethod;
import com.epam.cargo.infrastructure.web.Model;
import com.epam.cargo.infrastructure.web.data.page.Page;
import com.epam.cargo.infrastructure.web.data.pageable.Pageable;
import com.epam.cargo.infrastructure.web.data.sort.Order;
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
            HttpSession session,
            @Qualifier("applications")
            @PageableDefault(size = 7, sort = {"id"}, directions = {Order.Direction.DESC})
                    Pageable applicationsPageable,
            @RequestParam(name = "activePill", defaultValue = "pills-applications-tab")
                    String activePill

            ){
        User authorized = authorizationService.getAuthorized(session);
        model.addAttribute("user", authorized);
        Page<DeliveryApplication> applicationsPage = applicationService.findAllByUserId(authorized.getId(), applicationsPageable);
        model.addAttribute("applications", applicationsPage);
        model.addAttribute("activePill", activePill);
        return "profile.jsp";
    }
}
