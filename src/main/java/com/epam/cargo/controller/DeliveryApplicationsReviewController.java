package com.epam.cargo.controller;

import com.epam.cargo.infrastructure.annotation.Controller;
import com.epam.cargo.infrastructure.annotation.RequestMapping;
import com.epam.cargo.infrastructure.dispatcher.HttpMethod;
import com.epam.cargo.infrastructure.web.Model;

@Controller
public class DeliveryApplicationsReviewController {

    @RequestMapping(url = "/applications/review", method = HttpMethod.GET)
    public String reviewApplications(
            Model model
    ){
        model.addAttribute("url", "/applications/review");
        return "applicationsReview.jsp";
    }

}
