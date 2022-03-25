package com.epam.cargo.controller;

import com.epam.cargo.dto.DeliveryApplicationRequest;
import com.epam.cargo.entity.BaggageType;
import com.epam.cargo.infrastructure.annotation.Controller;
import com.epam.cargo.infrastructure.annotation.Inject;
import com.epam.cargo.infrastructure.annotation.PropertyValue;
import com.epam.cargo.infrastructure.annotation.RequestMapping;
import com.epam.cargo.infrastructure.dispatcher.HttpMethod;
import com.epam.cargo.infrastructure.web.Model;
import com.epam.cargo.infrastructure.web.data.sort.Order;
import com.epam.cargo.infrastructure.web.data.sort.Sort;
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

        return "makeDeliveryApplication.jsp";
    }

}
