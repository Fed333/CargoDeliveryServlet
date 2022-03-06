package com.epam.cargo.controller;

import com.epam.cargo.infrastructure.annotation.Controller;
import com.epam.cargo.infrastructure.annotation.RequestAttribute;
import com.epam.cargo.infrastructure.annotation.RequestMapping;
import com.epam.cargo.infrastructure.dispatcher.HttpMethod;
import com.epam.cargo.infrastructure.web.Model;

@SuppressWarnings("unused")
@Controller
public class DirectionsController {

    @RequestMapping(url = "/directions", method = HttpMethod.GET)
    public String directions(
            Model model,
            @RequestAttribute(name = "response", defaultValue = "default Response")
            String response
    ){

        model.addAttribute("response", response);
        return "directions.jsp";
    }
}
