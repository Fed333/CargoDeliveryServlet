package com.epam.cargo.controller;

import com.epam.cargo.dto.UserRequest;
import com.epam.cargo.entity.City;
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
import com.epam.cargo.service.CityService;
import com.epam.cargo.service.LocaleResolverService;
import com.epam.cargo.service.UserService;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

/**
 * Takes requests associated with registration user page.
 * @author Roman Kovalchuk
 * @version 1.0
 * */
@Controller
public class RegistrationController {

    @Inject
    private UserService userService;

    @Inject
    private CityService cityService;

    @Inject
    private LocaleResolverService localeResolverService;

    @PropertyValue
    private String messages;

    @RequestMapping(url = "/registration", method = HttpMethod.GET)
    public String registrationPage(
            UserRequest userRequest,
            Model model,
            HttpSession session

    ){
        Locale locale = localeResolverService.resolveLocale(session);
        List<City> cities = cityService.findAll(locale, Sort.by(new Order("name", Order.Direction.ASC)));
        model.addAttribute("cities", cities);
        model.addAttribute("userRequest", Optional.ofNullable(model.getAttribute("userRequest")).orElse(userRequest));

        return "registration.jsp";
    }

    @RequestMapping(url = "/registration", method = HttpMethod.POST)
    public String registration(
            HttpSession session,
            UserRequest userRequest,
            Model model,
            RedirectAttributes redirectAttributes
    ) {
        try {
            userService.registerUser(userRequest, localeResolverService.resolveLocale(session));
        } catch (WrongDataException e) {
            redirectAttributes
                    .addFlashAttribute("userRequest", userRequest)
                    .addFlashAttribute(e.getModelAttribute(), e.getMessage());
            return "redirect:/registration";
        }
        model.addAttribute("userRequest", userRequest);
        return "redirect:/login";
    }

}