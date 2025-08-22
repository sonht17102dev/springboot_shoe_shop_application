package com.sonht.e_commerce_webapp_spring_boot.controller.customer;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sonht.e_commerce_webapp_spring_boot.entity.Product;
import com.sonht.e_commerce_webapp_spring_boot.service.ProductService;

import org.springframework.web.bind.annotation.RequestBody;


@Controller
public class CartController {
    private final ProductService productService;

    public CartController(ProductService productService) {
        this.productService = productService;
    }

    @PostMapping("/signed-in")
    @ResponseBody
    public String checkUserLogin() {
        
        return "shopper/cart";
    }

    @PostMapping("/add-to-cart-header/{productId}")
    @ResponseBody
    public Product postMethodName(@PathVariable Long productId) {
        Product product = productService.findById(productId);
        
        return product;
    }
    
    @PostMapping("/cart-header/{productId}")
    @ResponseBody
    public Product postMethodNam1(@PathVariable Long productId) {
        Product product = productService.findById(productId);
        
        return product;
    }


    
}
