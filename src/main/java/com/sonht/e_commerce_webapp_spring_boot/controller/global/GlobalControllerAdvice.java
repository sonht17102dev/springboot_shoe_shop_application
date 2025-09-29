package com.sonht.e_commerce_webapp_spring_boot.controller.global;

import java.util.List;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import com.sonht.e_commerce_webapp_spring_boot.entity.Brand;
import com.sonht.e_commerce_webapp_spring_boot.service.BrandService;

// áp dụng cho tất cả các controller trong package com.sonht.e_commerce_webapp_spring_boot.controller.customer
@ControllerAdvice(basePackages = "com.sonht.e_commerce_webapp_spring_boot.controller.customer")
public class GlobalControllerAdvice {

    private final BrandService brandService;

    public GlobalControllerAdvice(BrandService brandService) {
        this.brandService = brandService;
    }

    @ModelAttribute("brands")
    public List<Brand> categories() {
        return brandService.findAll(); // trả về toàn bộ brands
    }
}

