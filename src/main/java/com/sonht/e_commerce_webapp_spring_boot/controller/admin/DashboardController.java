package com.sonht.e_commerce_webapp_spring_boot.controller.admin;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.sonht.e_commerce_webapp_spring_boot.dto.ProductDto;
import com.sonht.e_commerce_webapp_spring_boot.service.ProductService;


@Controller
@RequestMapping("admin")
public class DashboardController {

    private ProductService productService;

    public DashboardController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping({"/", "/home" })
    public String dashboard() {

        return "admin/layout";
    }

    
}
