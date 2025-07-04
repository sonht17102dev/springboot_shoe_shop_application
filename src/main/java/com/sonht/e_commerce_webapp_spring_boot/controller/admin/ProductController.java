package com.sonht.e_commerce_webapp_spring_boot.controller.admin;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.sonht.e_commerce_webapp_spring_boot.dto.ProductDto;
import com.sonht.e_commerce_webapp_spring_boot.service.ProductService;

@Controller
@RequestMapping("admin")
public class ProductController {
    
    private final ProductService productService;
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/products")
    public String productsData(Model model) {
        
        List<ProductDto> products = productService.findAll().stream()
        .map(product -> new ProductDto(product.getId(), product.getName(), product.getVersionName(), product.getDescription(), product.getPrice(), false,
                product.getStatus(), product.getCreatedAt(), product.getUpdatedAt()))
        .toList();

        model.addAttribute("products", products);
        System.out.println(products);

        return "admin/products";
    }

    @GetMapping("/products/create")
    public String getAddNewProductPage(Model model) {
        model.addAttribute("newProduct", new ProductDto());
        

        return "admin/products/add-product";
    }
}
