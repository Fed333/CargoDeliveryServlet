package com.epam.cargo.controller;

import com.epam.cargo.dto.UserRequest;
import com.epam.cargo.dto.validator.UserRequestValidator;
import com.epam.cargo.entity.City;
import com.epam.cargo.exception.WrongDataException;
import org.fed333.servletboot.annotation.Controller;
import org.fed333.servletboot.annotation.Inject;
import org.fed333.servletboot.annotation.PropertyValue;
import org.fed333.servletboot.annotation.RequestMapping;
import org.fed333.servletboot.dispatcher.HttpMethod;
import org.fed333.servletboot.web.Model;
import org.fed333.servletboot.web.data.sort.Order;
import org.fed333.servletboot.web.data.sort.Sort;
import org.fed333.servletboot.web.redirect.RedirectAttributes;
import com.epam.cargo.service.CityService;
import com.epam.cargo.service.LocaleResolverService;
import com.epam.cargo.service.UserService;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.ResourceBundle;

/**
 * Takes requests associated with registration user page.
 * @author Roman Kovalchuk
 * @version 1.0
 * */
@Controller
public class RegistrationController {

    private static final String USER_REQUEST = "userRequest";

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
        model.addAttribute(USER_REQUEST, Optional.ofNullable(model.getAttribute(USER_REQUEST)).orElse(userRequest));

        return "registration.jsp";
    }

    @RequestMapping(url = "/registration", method = HttpMethod.POST)
    public String registration(
            HttpSession session,
            UserRequest userRequest,
            Model model,
            RedirectAttributes redirectAttributes
    ) {
        Locale locale = localeResolverService.resolveLocale(session);
        UserRequestValidator validator = new UserRequestValidator(ResourceBundle.getBundle(messages, locale));
        if (validator.validate(userRequest)){
            try {
                userService.registerUser(userRequest, localeResolverService.resolveLocale(session));
            } catch (WrongDataException e) {
                redirectAttributes
                        .addFlashAttribute(USER_REQUEST, userRequest)
                        .addFlashAttribute(e.getModelAttribute(), e.getMessage());
                return "redirect:/registration";
            }
        } else {
            redirectAttributes.addFlashAttribute(USER_REQUEST, userRequest);
            validator.getErrors().forEach(redirectAttributes::addFlashAttribute);
            return "redirect:/registration";
        }

        model.addAttribute(USER_REQUEST, userRequest);
        return "redirect:/login";
    }

}