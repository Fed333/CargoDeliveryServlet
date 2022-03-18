package com.epam.cargo.controller;

import com.epam.cargo.infrastructure.annotation.Controller;
import com.epam.cargo.infrastructure.annotation.RequestMapping;
import com.epam.cargo.infrastructure.dispatcher.HttpMethod;
import com.epam.cargo.infrastructure.web.Model;

/**
 * Takes requests associated with error page.
 * @author Roman Kovalchuk
 * @version 1.0
 * */
@Controller
public class ErrorController {

    @RequestMapping(url = "/forbidden", method = HttpMethod.GET)
    public String forbidden(Model model) {
        model.addAttribute("url", "/forbidden");
        return "forbidden.jsp";
    }

}