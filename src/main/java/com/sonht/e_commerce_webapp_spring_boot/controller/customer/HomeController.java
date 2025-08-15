package com.sonht.e_commerce_webapp_spring_boot.controller.customer;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.sonht.e_commerce_webapp_spring_boot.dto.ProductDto;
import com.sonht.e_commerce_webapp_spring_boot.entity.Product;
import com.sonht.e_commerce_webapp_spring_boot.entity.ProductImage;
import com.sonht.e_commerce_webapp_spring_boot.service.ProductService;



@Controller
public class HomeController {

    private final ProductService productService;

    
    public HomeController(ProductService productService) {
        this.productService = productService;
    }



    @GetMapping({"/", "/index"})
    public String getHomePage(Model model) {
        List<Product> products = productService.findAllByIsDelete(true);
        
        // List<ProductDto> productDtos = products.stream()
        //         .map(product -> new ProductDto(
        //                 product.getId(),
        //                 product.getName(),
        //                 product.getVersionName(),
        //                 product.getDescription(),
        //                 product.getPrice(),
        //                 product.getIsDelete(),
        //                 product.getStatus(),
        //                 product.getBrands().get(0).getName(),
        //                 product.getProductImages().get(0).getImageUrl()))
        //         .toList();
        model.addAttribute("products", products);

        return "shopper/index";
    }
}
