package com.sonht.e_commerce_webapp_spring_boot.restcontroller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sonht.e_commerce_webapp_spring_boot.dto.ProductDto;
import com.sonht.e_commerce_webapp_spring_boot.service.ProductService;

// @RestController
// @RequestMapping("/api")
// public class ProductRestController {
    
//     private ProductService productService;

//     public ProductRestController(ProductService productService) {
//         this.productService = productService;
//     }

//     @GetMapping("/admin/products/data")
//     public List<ProductDto> productsData() {
//         List<ProductDto> products = productService.findAll().stream()
//         .map(product -> new ProductDto(product.getId(), product.getName(), product.getVersionName(), product.getPrice()))
//         .toList();
//         System.out.println(products);
//         return products;
//     }
// }
