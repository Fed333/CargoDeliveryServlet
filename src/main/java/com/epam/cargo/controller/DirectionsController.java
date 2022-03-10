package com.epam.cargo.controller;

import com.epam.cargo.dto.DirectionDeliveryFilterRequest;
import com.epam.cargo.entity.DirectionDelivery;
import com.epam.cargo.infrastructure.annotation.Controller;
import com.epam.cargo.infrastructure.annotation.Inject;
import com.epam.cargo.infrastructure.annotation.RequestMapping;
import com.epam.cargo.infrastructure.annotation.RequestParam;
import com.epam.cargo.infrastructure.dispatcher.HttpMethod;
import com.epam.cargo.infrastructure.web.Model;
import com.epam.cargo.service.DirectionDeliveryService;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Optional;

@SuppressWarnings("unused")
@Controller
public class DirectionsController {

    @Inject
    private DirectionDeliveryService directionsService;

    @RequestMapping(url = "/directions", method = HttpMethod.GET)
    public String directions(
            Model model,
            HttpSession session,
            @RequestParam(name = "senderCityName", defaultValue = "") String senderCity,
            @RequestParam(name = "receiverCityName", defaultValue = "") String receiverCity,
            DirectionDeliveryFilterRequest filter
    ){
        List<DirectionDelivery> directions = directionsService.findAll(filter);
        model.addAttribute("directions", directions);
        model.addAttribute("url", "/CargoDeliveryServlet/directions");

        Optional.ofNullable(filter.getSenderCityName()).ifPresent(v->session.setAttribute("senderCity", v));
        Optional.ofNullable(filter.getReceiverCityName()).ifPresent(v->session.setAttribute("receiverCity", v));

        return "directions.jsp";
    }
}
