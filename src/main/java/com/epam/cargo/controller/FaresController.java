package com.epam.cargo.controller;

import com.epam.cargo.entity.DimensionsFare;
import com.epam.cargo.entity.DistanceFare;
import com.epam.cargo.entity.WeightFare;
import org.fed333.servletboot.annotation.Controller;
import org.fed333.servletboot.annotation.Inject;
import org.fed333.servletboot.annotation.RequestMapping;
import org.fed333.servletboot.dispatcher.HttpMethod;
import org.fed333.servletboot.web.Model;
import com.epam.cargo.service.DimensionsFareService;
import com.epam.cargo.service.DistanceFareService;
import com.epam.cargo.service.WeightFareService;

import java.util.List;

/**
 * Takes requests associated with delivery fares page.
 * @author Roman Kovalchuk
 * @version 1.0
 * */
@Controller
public class FaresController {

    @Inject
    private DistanceFareService distanceFareService;

    @Inject
    private WeightFareService weightFareService;

    @Inject
    private DimensionsFareService dimensionsFareService;

    @RequestMapping(url = "/fares", method = HttpMethod.GET)
    public String faresPage(
        Model model
    ){
        List<DistanceFare> distanceFares = distanceFareService.findAllFares();
        List<WeightFare> weightFares = weightFareService.findAllFares();
        List<DimensionsFare> dimensionsFares = dimensionsFareService.findAllFares();

        model.addAttribute("distanceFares", distanceFares);
        model.addAttribute("weightFares", weightFares);
        model.addAttribute("dimensionsFares", dimensionsFares);

        return "fares.jsp";
    }
}
