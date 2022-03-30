package com.epam.cargo.controller;

import com.epam.cargo.dto.DeliveryReceiptRequest;
import com.epam.cargo.dto.validator.DeliveryReceiptRequestValidator;
import com.epam.cargo.entity.DeliveryApplication;
import com.epam.cargo.entity.DeliveryReceipt;
import com.epam.cargo.entity.User;
import com.epam.cargo.exception.ModelErrorAttribute;
import com.epam.cargo.exception.NotEnoughMoneyException;
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
import java.util.Optional;
import java.util.ResourceBundle;

/**
 * Takes requests associated with page of handling delivery receipt.
 * @author Roman Kovalchuk
 * @version 1.0
 * */
@Controller
public class DeliveryReceiptController {

    private final String APPLICATION_ID = "applicationId";
    private final String APPLICATION = "application";
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
            redirectAttributes.addFlashAttribute(APPLICATION, application);
            redirectAttributes.addFlashAttribute(APPLICATION_ID, application.getId());
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

    @RequestMapping(url = "/application/confirmation/failed", method = HttpMethod.GET)
    public String failedApplicationConfirmation(
            Long applicationId,
            Model model
    ){
        model.addAttribute("url", "/application/confirmation/failed");
        if (!Objects.isNull(applicationId)) {
            model.addAttribute(APPLICATION_ID, applicationId);
            model.addAttribute(APPLICATION, applicationService.findById(applicationId));
        }
        return "deliveryApplicationConfirmationFailed.jsp";
    }

    @RequestMapping(url = "/receipt", method = HttpMethod.GET)
    public String receiptPage(
            @RequestParam(name = "id", required = false) Long receiptId,
            Model model,
            RedirectAttributes redirectAttributes,
            HttpSession session
    ){
        User initiator = authorizationService.getAuthorized(session);
        Optional<DeliveryReceipt> optional = receiptService.findById(receiptId);
        if (optional.isEmpty()){
            redirectAttributes.addFlashAttribute(ModelErrorAttribute.ERROR_MESSAGE.getAttr(), "No found receipt with id " + receiptId);
            return "redirect:/error";
        }
        DeliveryReceipt receipt = optional.get();

        if (!initiator.isManager() && !userService.credentialsEquals(receipt.getCustomer(), initiator)){
            return "redirect:/forbidden";
        }

        model.addAttribute("receipt", receipt);
        return "receipt.jsp";
    }

    @RequestMapping(url = "/receipt/pay", method = HttpMethod.GET)
    public String payReceiptPage(
            @RequestParam(name = "id", required = false) Long receiptId,
            RedirectAttributes redirectAttributes,
            Model model,
            HttpSession session

    ){

        User initiator = authorizationService.getAuthorized(session);

        Optional<DeliveryReceipt> optional = receiptService.findById(receiptId);
        if (optional.isEmpty()){
            redirectAttributes.addFlashAttribute(ModelErrorAttribute.ERROR_MESSAGE.getAttr(), "No found receipt with id " + receiptId);
            return "redirect:/error";
        }
        DeliveryReceipt receipt = optional.get();

        if (!userService.credentialsEquals(receipt.getCustomer(), initiator)){
            return "redirect:/forbidden";
        }

        model.addAttribute("receipt", receipt);
        return "payReceipt.jsp";
    }

    @RequestMapping(url = "/receipt/pay", method = HttpMethod.POST)
    public String payReceipt(
            @RequestParam(name = "id", defaultValue = "-1") Long receiptId,
            RedirectAttributes redirectAttributes,
            Model model,
            HttpSession session
    ){

        User user = authorizationService.getAuthorized(session);

        Optional<DeliveryReceipt> optional = receiptService.findById(receiptId);
        if (optional.isEmpty()){
            redirectAttributes.addFlashAttribute(ModelErrorAttribute.ERROR_MESSAGE.getAttr(), "No found receipt with id " + receiptId);
            return "redirect:/error";
        }
        DeliveryReceipt receipt = optional.get();

        try {
            receiptService.payReceipt(receipt, user);
        } catch (NotEnoughMoneyException e) {
            redirectAttributes.addFlashAttribute(e.getAttribute(), e.getMessage());
            redirectAttributes.addFlashAttribute("rejectedFunds", e.getRejectedFunds());
            redirectAttributes.addFlashAttribute("price", e.getReceipt().getTotalPrice());
            return "redirect:/paying/failed";
        }
        return "redirect:/profile";
    }

}