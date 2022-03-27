package com.epam.cargo.controller;

import com.epam.cargo.dto.DeliveryApplicationsReviewFilterRequest;
import com.epam.cargo.entity.BaggageType;
import com.epam.cargo.entity.DeliveryApplication;
import com.epam.cargo.infrastructure.annotation.Controller;
import com.epam.cargo.infrastructure.annotation.Inject;
import com.epam.cargo.infrastructure.annotation.PageableDefault;
import com.epam.cargo.infrastructure.annotation.RequestMapping;
import com.epam.cargo.infrastructure.dispatcher.HttpMethod;
import com.epam.cargo.infrastructure.web.Model;
import com.epam.cargo.infrastructure.web.data.page.Page;
import com.epam.cargo.infrastructure.web.data.pageable.Pageable;
import com.epam.cargo.infrastructure.web.data.sort.Order;
import com.epam.cargo.service.CityService;
import com.epam.cargo.service.DeliveryApplicationService;
import com.epam.cargo.service.LocaleResolverService;

import javax.servlet.http.HttpSession;
import java.util.Locale;

@Controller
public class DeliveryApplicationsReviewController {

    @Inject
    private DeliveryApplicationService applicationService;

    @Inject
    private CityService cityService;

    @Inject
    private LocaleResolverService localeResolverService;

    @RequestMapping(url = "/applications/review", method = HttpMethod.GET)
    public String reviewApplications(
            Model model,
            DeliveryApplicationsReviewFilterRequest applicationsRequest,
            @PageableDefault(size = 6, sort = {"id"}, directions = {Order.Direction.DESC}) Pageable pageable,
            HttpSession session
    ){

        Locale locale = localeResolverService.resolveLocale(session);

        Page<DeliveryApplication> applications = applicationService.getPage(applicationsRequest, pageable);
        model.addAttribute("applicationsPage", applications);
        model.addAttribute("states", DeliveryApplication.State.values());
        model.addAttribute("types", BaggageType.values());
        model.addAttribute("cities", cityService.findAll());
        model.addAttribute("lang", locale.getLanguage());

        return "applicationsReview.jsp";
    }

}
