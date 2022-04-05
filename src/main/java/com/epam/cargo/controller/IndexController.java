package com.epam.cargo.controller;

import org.fed333.servletboot.annotation.Controller;
import org.fed333.servletboot.annotation.RequestMapping;
import org.fed333.servletboot.dispatcher.HttpMethod;
import org.fed333.servletboot.web.Model;

@Controller
public class IndexController {

    @RequestMapping(url="/", method = HttpMethod.GET)
    public String index(
        Model model
    ){
        model.addAttribute("greetings", "Hello from IndexController");
        return "index.jsp";
    }
}
