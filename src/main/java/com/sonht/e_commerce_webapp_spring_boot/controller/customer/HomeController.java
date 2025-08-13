package com.sonht.e_commerce_webapp_spring_boot.controller.customer;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.sonht.e_commerce_webapp_spring_boot.service.ProductService;



@Controller
public class HomeController {

    private final ProductService productService;

    
    public HomeController(ProductService productService) {
        this.productService = productService;
    }



    @GetMapping({"/", "/index"})
    public String getHomePage(Model model) {
        // model.addAttribute("products", productService.findAllByIsDelete(false));
        return "shopper/index";
    }
}
