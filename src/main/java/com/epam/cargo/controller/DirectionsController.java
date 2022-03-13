package com.epam.cargo.controller;

import com.epam.cargo.dto.DirectionDeliveryFilterRequest;
import com.epam.cargo.dto.PageRequest;
import com.epam.cargo.dto.SortRequest;
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
import java.util.Objects;
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
            DirectionDeliveryFilterRequest filter,
            //TODO infrastructure's support of Pageable
            PageRequest pageRequest
    ){
        List<DirectionDelivery> directions = directionsService.findAll(filter, pageRequest);

        model.addAttribute("directions", directions);
        model.addAttribute("url", "/CargoDeliveryServlet/directions");

        Optional.ofNullable(filter.getSenderCityName()).ifPresent(v->session.setAttribute("senderCity", v));
        Optional.ofNullable(filter.getReceiverCityName()).ifPresent(v->session.setAttribute("receiverCity", v));
        SortRequest sort = Optional.ofNullable(pageRequest).map(PageRequest::getSort).orElse(null);
        if (Objects.nonNull(sort)){
            Optional.ofNullable(sort.getProperty()).ifPresent(v->session.setAttribute("sort", v));
            Optional.ofNullable(sort.getOrder()).ifPresent(v->session.setAttribute("sortOrder", v));
        }
        Optional.ofNullable(pageRequest).ifPresent(v->model.addAttribute("pageRequest", pageRequest));
        return "directions.jsp";
    }
}
