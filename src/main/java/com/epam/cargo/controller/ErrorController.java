package com.epam.cargo.controller;

import org.fed333.servletboot.annotation.Controller;
import org.fed333.servletboot.annotation.RequestMapping;
import org.fed333.servletboot.annotation.RequestParam;
import org.fed333.servletboot.dispatcher.HttpMethod;
import org.fed333.servletboot.web.Model;

import java.util.Objects;

/**
 * Takes requests associated with error page.
 * @author Roman Kovalchuk
 * @version 1.0
 * */
@Controller
public class ErrorController {

    private final String ERROR_MESSAGE = "errorMessage";

    @RequestMapping(url = "/forbidden", method = HttpMethod.GET)
    public String forbidden(
            @RequestParam(name = ERROR_MESSAGE, required = false) String message,
            Model model
    ) {
        if (Objects.nonNull(message)){
            model.addAttribute(ERROR_MESSAGE, message);
        }
        return "forbidden.jsp";
    }

    @RequestMapping(url = "/error", method = HttpMethod.GET)
    public String error(
            @RequestParam(name = ERROR_MESSAGE, required = false) String message,
            Model model
    ){
        if (Objects.nonNull(message)){
            model.addAttribute(ERROR_MESSAGE, message);
        }
        return "error.jsp";
    }

}