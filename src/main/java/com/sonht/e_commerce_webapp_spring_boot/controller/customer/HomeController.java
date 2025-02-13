package com.sonht.e_commerce_webapp_spring_boot.controller.customer;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;



@Controller
public class HomeController {

    @GetMapping({"/", "/index"})
    public  String home() {
        
        return "shopper/index";
    }
}
