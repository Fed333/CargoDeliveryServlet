package com.epam.cargo.controller;

import com.epam.cargo.dto.DeliveryReceiptRequest;
import com.epam.cargo.dto.validator.DeliveryReceiptRequestValidator;
import com.epam.cargo.entity.DeliveryApplication;
import com.epam.cargo.entity.User;
import com.epam.cargo.exception.ModelErrorAttribute;
import com.epam.cargo.exception.WrongDataException;
import com.epam.cargo.exception.WrongInput;
import com.epam.cargo.infrastructure.annotation.*;
import com.epam.cargo.infrastructure.dispatcher.HttpMethod;
import com.epam.cargo.infrastructure.web.Model;
import com.epam.cargo.infrastructure.web.redirect.RedirectAttributes;
import com.epam.cargo.service.*;

import javax.servlet.http.HttpSession;
import java.util.Locale;
import java.util.Objects;
import java.util.ResourceBundle;

/**
 * Takes requests associated with page of handling delivery receipt.
 * @author Roman Kovalchuk
 * @version 1.0
 * */
@Controller
public class DeliveryReceiptController {

    @Inject
    private DeliveryReceiptService receiptService;

    @Inject
    private DeliveryApplicationService applicationService;

    @Inject
    private UserService userService;

    @Inject
    private AuthorizationService authorizationService;

    @Inject
    private LocaleResolverService localeResolverService;

    @PropertyValue
    private String messages;

    @RequestMapping(url = "/application/accept", method = HttpMethod.GET)
    public String acceptApplicationPage(
            @RequestParam(name = "id", defaultValue = "-1") Long applicationId,
            @RequestParam(name = "price", required = false) Double price,
            Model model,
            HttpSession session
    ){
        DeliveryApplication application = applicationService.findById(applicationId);
        User manager = authorizationService.getAuthorized(session);

        if (Objects.isNull(price)){
            price = application.getPrice();
        }

        model.addAttribute("price", price);
        model.addAttribute("application", application);
        model.addAttribute("customer", application.getCustomer());
        model.addAttribute("manager", manager);
        return "deliveryReceipt.jsp";
    }

    @RequestMapping(url = "/application/accept", method = HttpMethod.POST)
    public String makeReceipt(
            @RequestParam(name = "id") Long applicationId,
            DeliveryReceiptRequest receiptRequest,
            RedirectAttributes redirectAttributes,
            Model model,
            HttpSession session
    ){
        Locale locale = localeResolverService.resolveLocale(session);
        if (Objects.isNull(applicationId)){
            redirectAttributes.addFlashAttribute(ModelErrorAttribute.RECEIPT.getAttr(), WrongInput.MISSING_RECEIPT_PRICE);
            return "redirect:/error";
        }

        DeliveryApplication application = applicationService.findById(applicationId);
        User manager = authorizationService.getAuthorized(session);

        if(!application.getState().equals(DeliveryApplication.State.SUBMITTED)){
            redirectAttributes.addFlashAttribute("application", application);
            redirectAttributes.addFlashAttribute("applicationId", application.getId());
            return "redirect:/application/confirmation/failed";
        }

        boolean successfullyMade = false;
        DeliveryReceiptRequestValidator validator = new DeliveryReceiptRequestValidator(ResourceBundle.getBundle(messages, locale));
        if(validator.validate(receiptRequest)){
            try {
                successfullyMade = receiptService.makeReceipt(application, manager, receiptRequest);
            }
            catch (WrongDataException e){
                model.addAttribute(e.getModelAttribute(), e.getMessage());
            }
        }
        else{
            model.mergeAttributes(validator.getErrors());
        }

        if (!successfullyMade){
            model.asMap().forEach(redirectAttributes::addFlashAttribute);
            return String.format("redirect:/application/accept?id=%d", application.getId());
        }

        return "redirect:/applications/review";

    }

}