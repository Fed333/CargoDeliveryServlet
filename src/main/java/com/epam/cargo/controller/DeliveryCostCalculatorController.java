package com.epam.cargo.controller;

import com.epam.cargo.dto.DeliveryCostCalculatorRequest;
import com.epam.cargo.dto.DeliveryCostCalculatorResponse;
import com.epam.cargo.exception.WrongDataException;
import org.fed333.servletboot.annotation.*;
import org.fed333.servletboot.dispatcher.HttpMethod;
import org.fed333.servletboot.web.Model;
import org.fed333.servletboot.web.data.sort.Order;
import org.fed333.servletboot.web.data.sort.Sort;
import org.fed333.servletboot.web.redirect.RedirectAttributes;
import com.epam.cargo.service.CityService;
import com.epam.cargo.service.DeliveryCostCalculatorService;
import com.epam.cargo.service.LocaleResolverService;

import javax.servlet.http.HttpSession;
import java.util.Locale;

/**
 * Takes requests associated with page of handling delivery cost calculator.
 * @author Roman Kovalchuk
 * @version 1.0
 * */
@Controller
public class DeliveryCostCalculatorController {

    private static final String CALCULATOR_REQUEST = "calculatorRequest";

    @Inject
    private DeliveryCostCalculatorService deliveryCostCalculatorService;

    @Inject
    private CityService cityService;

    @PropertyValue
    private String messages;

    @Inject
    private LocaleResolverService localeResolverService;

    @RequestMapping(url = "/delivery_cost_calculator", method = HttpMethod.GET)
    public String calculatorPage(
            DeliveryCostCalculatorRequest calculatorRequest,
            Model model,
            HttpSession session
    ){
        Locale locale = localeResolverService.resolveLocale(session);
        model.addAttribute("cities", cityService.findAll(locale, Sort.by(Order.by("name"))));
        if (!model.containsAttribute(CALCULATOR_REQUEST)){
            model.addAttribute(CALCULATOR_REQUEST, calculatorRequest);
        }
        return "deliveryCostCalculator.jsp";
    }

    @RequestMapping(url = "/delivery_cost_calculator", method = HttpMethod.POST)
    public String calculateCost(
            DeliveryCostCalculatorRequest calculatorRequest,
            @RequestParam(name = "cityFromId", defaultValue = "") String cityFromId,
            HttpSession session,
            RedirectAttributes redirectAttributes
    ){
        Locale locale = localeResolverService.resolveLocale(session);
        try {
            DeliveryCostCalculatorResponse response = deliveryCostCalculatorService.calculateCost(calculatorRequest, locale);
            redirectAttributes.addFlashAttribute("cost", response.getCost());
            redirectAttributes.addFlashAttribute("distance", response.getDistance());
        } catch (WrongDataException e) {
            redirectAttributes.addFlashAttribute(e.getModelAttribute(), e.getMessage());
        }
        redirectAttributes.addFlashAttribute(CALCULATOR_REQUEST, calculatorRequest);
        return "redirect:/delivery_cost_calculator";
    }
}
