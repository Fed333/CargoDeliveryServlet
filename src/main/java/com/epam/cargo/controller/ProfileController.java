package com.epam.cargo.controller;

import com.epam.cargo.entity.DeliveryApplication;
import com.epam.cargo.entity.DeliveryReceipt;
import com.epam.cargo.entity.User;
import org.fed333.servletboot.annotation.*;
import org.fed333.servletboot.dispatcher.HttpMethod;
import org.fed333.servletboot.web.Model;
import org.fed333.servletboot.web.data.page.Page;
import org.fed333.servletboot.web.data.pageable.Pageable;
import org.fed333.servletboot.web.data.sort.Order;
import com.epam.cargo.service.AuthorizationService;
import com.epam.cargo.service.UserService;

import javax.servlet.http.HttpSession;
import java.util.Objects;

@Controller
public class ProfileController {

    @Inject
    private UserService userService;

    @Inject
    private AuthorizationService authorizationService;

    @RequestMapping(url = "/profile", method = HttpMethod.GET)
    public String profilePage(
            Model model,
            HttpSession session,
            @Qualifier("applications")
            @PageableDefault(size = 7, sort = {"id"}, directions = {Order.Direction.DESC})
                    Pageable applicationsPageable,
            @Qualifier("receipts")
            @PageableDefault(size = 5, sort = {"id"}, directions = {Order.Direction.DESC})
                    Pageable receiptsPageable,
            @RequestParam(name = "activePill", defaultValue = "pills-applications-tab")
                    String activePill
    ){
        User authorized = authorizationService.getAuthorized(session);
        model.addAttribute("user", authorized);
        Page<DeliveryApplication> applicationsPage = userService.getApplications(authorized, applicationsPageable);
        Page<DeliveryReceipt> receiptPage = userService.getCustomerReceipts(authorized, receiptsPageable);
        model.addAttribute("applications", applicationsPage);
        model.addAttribute("receipts", receiptPage);
        model.addAttribute("activePill", activePill);
        return "profile.jsp";
    }

    @RequestMapping(url = "/profile/review", method = HttpMethod.GET)
    public String profilePage(
            @RequestParam(name = "id", defaultValue = "-1") Long customerId,
            @Qualifier("applications")
            @PageableDefault(size = 7, sort = {"id"}, directions = {Order.Direction.DESC})
                    Pageable applicationsPageable,
            @Qualifier("receipts")
            @PageableDefault(size = 5, sort = {"id"}, directions = {Order.Direction.DESC})
                    Pageable receiptsPageable,
            @RequestParam(name = "activePill", defaultValue = "pills-applications-tab")
                    String activePill,
            Model model
    ){
        User customer = userService.findUserById(customerId);
        if (Objects.isNull(customer)){
            return "redirect:/forbidden";
        }

        Page<DeliveryApplication> applications = userService.getApplications(customer, applicationsPageable);
        Page<DeliveryReceipt> receipts = userService.getCustomerReceipts(customer, receiptsPageable);
        model.addAttribute("applications", applications);
        model.addAttribute("receipts", receipts);
        model.addAttribute("user", customer);
        model.addAttribute("activePill", activePill);

        return "profileReview.jsp";
    }

}
