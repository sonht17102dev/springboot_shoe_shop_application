package com.sonht.e_commerce_webapp_spring_boot.controller.customer;

import java.util.List;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.sonht.e_commerce_webapp_spring_boot.dto.ProductDto;
import com.sonht.e_commerce_webapp_spring_boot.entity.Product;
import com.sonht.e_commerce_webapp_spring_boot.entity.ProductImage;
import com.sonht.e_commerce_webapp_spring_boot.entity.User;
import com.sonht.e_commerce_webapp_spring_boot.service.BrandService;
import com.sonht.e_commerce_webapp_spring_boot.service.ProductService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@Controller
public class HomeController {

    private final ProductService productService;
    private final BrandService brandService;

    public HomeController(ProductService productService, BrandService brandService) {
        this.productService = productService;
        this.brandService = brandService;
    }

    @GetMapping({ "/", "/index" })
    public String getHomePage(Model model, HttpServletRequest request) {
        List<Product> products = productService.findAllByStatus("Đang bán");
        model.addAttribute("products", products);
        model.addAttribute("brands", brandService.findAll());
        
        return "shopper/index";
    }

    @GetMapping("/search")
    public String searchProducts(Model model, @RequestParam("q") String keyword) {
        List<Product> products = productService.searchProducts(keyword);
        model.addAttribute("products", products);
        model.addAttribute("text", keyword);
        model.addAttribute("brands", brandService.findAll());
        return "shopper/search";
    }

    
}
