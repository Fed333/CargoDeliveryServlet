package com.epam.cargo.controller;

import com.epam.cargo.infrastructure.annotation.Controller;
import com.epam.cargo.infrastructure.annotation.RequestMapping;
import com.epam.cargo.infrastructure.dispatcher.HttpMethod;
import com.epam.cargo.infrastructure.web.Model;

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
