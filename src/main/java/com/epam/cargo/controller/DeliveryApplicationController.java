package com.epam.cargo.controller;

import com.epam.cargo.dto.DeliveryApplicationRequest;
import com.epam.cargo.dto.validator.DeliveredBaggageRequestValidator;
import com.epam.cargo.dto.validator.DeliveryApplicationRequestValidator;
import com.epam.cargo.entity.BaggageType;
import com.epam.cargo.entity.City;
import com.epam.cargo.entity.DeliveryApplication;
import com.epam.cargo.entity.User;
import com.epam.cargo.exception.ModelErrorAttribute;
import com.epam.cargo.exception.NoExistingCityException;
import com.epam.cargo.exception.NoExistingDirectionException;
import com.epam.cargo.exception.WrongDataException;
import com.epam.cargo.infrastructure.annotation.*;
import com.epam.cargo.infrastructure.dispatcher.HttpMethod;
import com.epam.cargo.infrastructure.web.Model;
import com.epam.cargo.infrastructure.web.data.sort.Order;
import com.epam.cargo.infrastructure.web.data.sort.Sort;
import com.epam.cargo.infrastructure.web.redirect.RedirectAttributes;
import com.epam.cargo.service.*;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.ResourceBundle;

import static com.epam.cargo.exception.ModelErrorAttribute.ERROR_MESSAGE;

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

        DeliveryApplicationRequestValidator validator = new DeliveryApplicationRequestValidator(ResourceBundle.getBundle(messages, locale));
        validator.validate(applicationRequest);

        if (!validator.hasErrors()) {
            try {
                successfullySent = applicationService.sendApplication(customer, applicationRequest, locale);
            } catch (WrongDataException e) {
                model.addAttribute(e.getModelAttribute(), e.getMessage());
            }
        } else {
            model.mergeAttributes(validator.getErrors());
        }
        if (!successfullySent) {
            model.asMap().forEach(redirectAttributes::addFlashAttribute);
            redirectAttributes.addFlashAttribute(APPLICATION_REQUEST, applicationRequest);
            return "redirect:/make_app";
        }

        return "redirect:/profile";
    }

    @RequestMapping(url = "/application", method = HttpMethod.GET)
    public String applicationPage(
            @RequestParam(name = "id", defaultValue = "-1") Long applicationId,
            Model model
    ){
        if (applicationId == -1){
            return "redirect:/forbidden";
        }

        DeliveryApplication application = applicationService.findById(applicationId);
        model.addAttribute("application", application);

        return "application.jsp";
    }

    @RequestMapping(url = "/application/complete", method = HttpMethod.POST)
    public String completeApplication(
            @RequestParam(name = "id", required = false) Long applicationId,
            Model model
    ){
        DeliveryApplication application = applicationService.findById(applicationId);
        applicationService.completeApplication(application);
        return String.format("redirect:/application?id=%d", application.getId());
    }

    @RequestMapping(url  = "/application/reject", method = HttpMethod.POST)
    public String rejectApplication(
            @RequestParam(name = "id", required = false) Long applicationId
    ){
        DeliveryApplication application = applicationService.findById(applicationId);
        applicationService.rejectApplication(application);
        return "redirect:/applications/review";
    }

    @RequestMapping(url = "/application/update", method = HttpMethod.GET)
    public String updateApplicationPage(
            @RequestParam(name = "id", defaultValue = "-1") Long applicationId,
            Model model,
            RedirectAttributes redirectAttributes,
            HttpSession session
    ){
        User initiator = authorizationService.getAuthorized(session);
        Locale locale = localeResolverService.resolveLocale(session);
        DeliveryApplication application = applicationService.findById(applicationId);

        if (Objects.isNull(application)){
            redirectAttributes.addFlashAttribute(ERROR_MESSAGE.getAttr(), "No found delivery applications with id: " + applicationId);
            return "redirect:/error";
        }

        if (!application.getState().equals(DeliveryApplication.State.SUBMITTED)){
            return "redirect:/forbidden";
        }

        if (!initiator.isManager() && !userService.credentialsEquals(application.getCustomer(), initiator)){
            return "redirect:/forbidden";
        }

        model.addAttribute("application", application);
        model.addAttribute("baggageTypes", BaggageType.values());
        List<City> cities = cityService.findAll(locale, Sort.by(Order.by("name")));
        model.addAttribute("cities", cities);

        return "updateApplication.jsp";
    }

    @RequestMapping(url = "/application/update", method = HttpMethod.POST)
    public String updateApplication(
            @RequestParam(name = "id", defaultValue = "-1") Long applicationId,
            DeliveryApplicationRequest updated,
            Model model,
            RedirectAttributes redirectAttributes,
            HttpSession session
    ){
        Locale locale = localeResolverService.resolveLocale(session);
        User initiator = authorizationService.getAuthorized(session);
        DeliveryApplication application = applicationService.findById(applicationId);
        if (Objects.isNull(application)){
            redirectAttributes.addFlashAttribute(ERROR_MESSAGE.getAttr(), "No found delivery applications with id: " + applicationId);
            return "redirect:/error";
        }
        DeliveryApplicationRequestValidator validator = new DeliveryApplicationRequestValidator(ResourceBundle.getBundle(messages, locale));
        if (validator.validate(updated)){
            if (!initiator.isManager() && !userService.credentialsEquals(application.getCustomer(), initiator)){
                return "redirect:/forbidden";
            }
            try {
                applicationService.edit(application, updated);
            } catch (NoExistingCityException | NoExistingDirectionException e) {
                model.addAttribute(e.getModelAttribute(), e.getMessage());
            }
        }
        else {
            validator.getErrors().forEach(redirectAttributes::addFlashAttribute);
            return String.format("redirect:/application/update?id=%d", application.getId());
        }
        return String.format("redirect:/application?id=%d", application.getId());
    }

}
