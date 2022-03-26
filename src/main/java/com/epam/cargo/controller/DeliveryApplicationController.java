package com.epam.cargo.controller;

import com.epam.cargo.dto.DeliveryApplicationRequest;
import com.epam.cargo.entity.BaggageType;
import com.epam.cargo.entity.User;
import com.epam.cargo.exception.WrongDataException;
import com.epam.cargo.infrastructure.annotation.Controller;
import com.epam.cargo.infrastructure.annotation.Inject;
import com.epam.cargo.infrastructure.annotation.PropertyValue;
import com.epam.cargo.infrastructure.annotation.RequestMapping;
import com.epam.cargo.infrastructure.dispatcher.HttpMethod;
import com.epam.cargo.infrastructure.web.Model;
import com.epam.cargo.infrastructure.web.data.sort.Order;
import com.epam.cargo.infrastructure.web.data.sort.Sort;
import com.epam.cargo.infrastructure.web.redirect.RedirectAttributes;
import com.epam.cargo.service.*;

import javax.servlet.http.HttpSession;
import java.util.Locale;

/**
 * Takes requests associated with page of handling delivery application.
 * @author Roman Kovalchuk
 * @version 1.0
 * */
@Controller
public class DeliveryApplicationController {

    private final String APPLICATION_REQUEST = "deliveryApplicationRequest";
    @Inject
    private DeliveryApplicationService applicationService;

    @Inject
    private CityService cityService;

    @Inject
    private UserService userService;

    @Inject
    private AuthorizationService authorizationService;

    @Inject
    private LocaleResolverService localeResolverService;

    @PropertyValue
    private String messages;

    @RequestMapping(url = "/make_app", method = HttpMethod.GET)
    public String makeApplicationPage(
            DeliveryApplicationRequest applicationRequest,
            Model model,
            HttpSession session
    ){
        Locale locale = localeResolverService.resolveLocale(session);

        model.addAttribute("types", BaggageType.values());
        model.addAttribute("cities", cityService.findAll(locale, Sort.by(Order.by("name"))));

        model.asMap().putIfAbsent(APPLICATION_REQUEST, applicationRequest);

        return "makeDeliveryApplication.jsp";
    }

    @RequestMapping(url = "/make_app", method = HttpMethod.POST)
    public String sendApplication(
            DeliveryApplicationRequest applicationRequest,
            RedirectAttributes redirectAttributes,
            Model model,
            HttpSession session
    ){
        Locale locale = localeResolverService.resolveLocale(session);
        User customer = authorizationService.getAuthorized(session);
        boolean successfullySent = false;

        try {
            successfullySent = applicationService.sendApplication(customer, applicationRequest, locale);
        } catch (WrongDataException e) {
            model.addAttribute(e.getModelAttribute(), e.getMessage());
        }

        if (!successfullySent) {
            model.asMap().forEach(redirectAttributes::addFlashAttribute);
            redirectAttributes.addFlashAttribute(APPLICATION_REQUEST, applicationRequest);
            return "redirect:/make_app";
        }

        return "redirect:/profile";
    }

}
