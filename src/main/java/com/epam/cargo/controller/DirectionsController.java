package com.epam.cargo.controller;

import com.epam.cargo.entity.DirectionDelivery;
import com.epam.cargo.infrastructure.annotation.Controller;
import com.epam.cargo.infrastructure.annotation.Inject;
import com.epam.cargo.infrastructure.annotation.RequestAttribute;
import com.epam.cargo.infrastructure.annotation.RequestMapping;
import com.epam.cargo.infrastructure.dispatcher.HttpMethod;
import com.epam.cargo.infrastructure.web.Model;
import com.epam.cargo.service.DirectionDeliveryService;

import java.util.List;

@SuppressWarnings("unused")
@Controller
public class DirectionsController {

    @Inject
    private DirectionDeliveryService directionsService;

    @RequestMapping(url = "/directions", method = HttpMethod.GET)
    public String directions(
            Model model,
            @RequestAttribute(name = "response", defaultValue = "default Response")
            String response
    ){
        List<DirectionDelivery> directions = directionsService.findAll();
        model.addAttribute("directions", directions);
        model.addAttribute("response", response);
        return "directions.jsp";
    }
}
