package com.sonht.e_commerce_webapp_spring_boot.controller.customer;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.sonht.e_commerce_webapp_spring_boot.dto.OrderWebDto;


@Controller
public class OrderCustomerController {
    

    @PostMapping("/checkout")
    public String handleOrder(@ModelAttribute OrderWebDto orderWebDto) {
        
        
        return "shopper/checkout";
    }
    
}
