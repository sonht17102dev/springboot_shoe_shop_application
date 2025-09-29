package com.sonht.e_commerce_webapp_spring_boot.controller.admin;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.sonht.e_commerce_webapp_spring_boot.service.ProductService;


@Controller
@RequestMapping("admin")
public class DashboardController {

    private ProductService productService;

    public DashboardController(ProductService productService) {
        this.productService = productService;
    }

    /*
     * Xử lý trang dashboard
     */
    @GetMapping({"/", "/home" })
    public String dashboard() {

        return "admin/layout";
    }

    
}
