package com.epam.cargo.controller;

import com.epam.cargo.dto.DirectionDeliveryFilterRequest;
import com.epam.cargo.entity.DirectionDelivery;
import com.epam.cargo.infrastructure.annotation.*;
import com.epam.cargo.infrastructure.dispatcher.HttpMethod;
import com.epam.cargo.infrastructure.web.Model;
import com.epam.cargo.infrastructure.web.data.page.Page;
import com.epam.cargo.infrastructure.web.data.pageable.Pageable;
import com.epam.cargo.infrastructure.web.data.sort.Order;
import com.epam.cargo.service.DirectionDeliveryService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Optional;

@SuppressWarnings("unused")
@Controller
public class DirectionsController {

    @Inject
    private DirectionDeliveryService directionsService;

    @RequestMapping(url = "/directions", method = HttpMethod.GET)
    public String directions(
            Model model,
            HttpServletRequest req,
            HttpSession session,
            DirectionDeliveryFilterRequest filter,
            @Qualifier("directions")
            @PageableDefault(size = 9, sort = {"distance", "senderCity.name"}, directions = {Order.Direction.DESC, Order.Direction.ASC})
                    Pageable pageable
    ){
        Page<DirectionDelivery> directions = directionsService.findAll(filter, pageable);

        model.addAttribute("directions", directions);

        Optional.ofNullable(filter.getSenderCityName()).ifPresent(v->session.setAttribute("senderCity", v));
        Optional.ofNullable(filter.getReceiverCityName()).ifPresent(v->session.setAttribute("receiverCity", v));
        Order order = pageable.getSort().getOrders().get(0);
        model.addAttribute("order", order);
        session.setAttribute("sort", order.getProperty());
        session.setAttribute("sortOrder", order.getDirection().toString());
        model.addAttribute("pageable", pageable);

        return "directions.jsp";
    }
}