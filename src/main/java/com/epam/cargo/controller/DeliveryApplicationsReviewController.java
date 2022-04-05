package com.epam.cargo.controller;

import com.epam.cargo.dto.DeliveryApplicationsReviewFilterRequest;
import com.epam.cargo.entity.BaggageType;
import com.epam.cargo.entity.DeliveryApplication;
import org.fed333.servletboot.annotation.Controller;
import org.fed333.servletboot.annotation.Inject;
import org.fed333.servletboot.annotation.PageableDefault;
import org.fed333.servletboot.annotation.RequestMapping;
import org.fed333.servletboot.dispatcher.HttpMethod;
import org.fed333.servletboot.web.Model;
import org.fed333.servletboot.web.data.page.Page;
import org.fed333.servletboot.web.data.pageable.Pageable;
import org.fed333.servletboot.web.data.sort.Order;
import org.fed333.servletboot.web.data.sort.Sort;
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
        model.addAttribute("cities", cityService.findAll(locale, Sort.by(Order.by("name"))));
        model.addAttribute("lang", locale.getLanguage());
        model.addAttribute("deliveryApplicationsReviewFilterRequest", applicationsRequest);

        return "applicationsReview.jsp";
    }

}
