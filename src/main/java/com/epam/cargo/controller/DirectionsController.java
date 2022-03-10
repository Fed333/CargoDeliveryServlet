package com.epam.cargo.controller;

import com.epam.cargo.dto.DirectionDeliveryFilterRequest;
import com.epam.cargo.entity.DirectionDelivery;
import com.epam.cargo.infrastructure.annotation.*;
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
            @RequestParam(name = "senderCityName", defaultValue = "") String senderCity,
            @RequestParam(name = "receiverCityName", defaultValue = "") String receiverCity,
            DirectionDeliveryFilterRequest filter
    ){
        List<DirectionDelivery> directions = directionsService.findAll(filter);
        model.addAttribute("directions", directions);
        model.addAttribute("url", "/CargoDeliveryServlet/directions");
        return "directions.jsp";
    }
}
