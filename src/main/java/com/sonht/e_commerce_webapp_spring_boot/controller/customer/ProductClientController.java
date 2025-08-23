package com.sonht.e_commerce_webapp_spring_boot.controller.customer;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import com.sonht.e_commerce_webapp_spring_boot.entity.Product;
import com.sonht.e_commerce_webapp_spring_boot.service.BrandService;
import com.sonht.e_commerce_webapp_spring_boot.service.CategoryService;
import com.sonht.e_commerce_webapp_spring_boot.service.ColorService;
import com.sonht.e_commerce_webapp_spring_boot.service.ProductService;
import com.sonht.e_commerce_webapp_spring_boot.service.ProductSizeService;

import jakarta.servlet.http.HttpServletRequest;

@Controller
public class ProductClientController {
    private final CategoryService categoryService;
    private final BrandService brandService;
    private final ProductSizeService productSizeService;
    private final ColorService colorService;
    private final ProductService productService;

    public ProductClientController(CategoryService categoryService, BrandService brandService,
            ProductSizeService productSizeService, ColorService colorService, ProductService productService) {
        this.categoryService = categoryService;
        this.brandService = brandService;
        this.productSizeService = productSizeService;
        this.colorService = colorService;
        this.productService = productService;
    }

    @GetMapping("/product-listing")
    public String getProductListingPage(Model model,
            @RequestParam(required = false) Long categoryId,
            @RequestParam(required = false) Long brandId,
            @RequestParam(required = false) Long priceMin,
            @RequestParam(required = false) Long priceMax,
            @RequestParam(required = false) Integer size,
            @RequestParam(required = false) Long colorId) {
        model.addAttribute("categories", categoryService.findAll());
        model.addAttribute("brands", brandService.findAll());
        model.addAttribute("sizes", productSizeService.findAll());
        model.addAttribute("colors", colorService.findAll());
        List<Product> products = null;
        if(categoryId == null && brandId == null && priceMin == null && priceMax == null && size == null && colorId == null) {
            products = productService.findAllByStatus("Đang bán");
        } 
        if(categoryId != null) {
            products = productService.filterProducts(categoryId, null, null, null, null, null);
        }
        if(brandId != null) {
            products = brandService.findProductsByBrandId(brandId);
        }
        if(priceMax != null ) {
            products = productService.filterProducts(null, null, null, priceMax, null, null);
        }
        if(priceMax != null && priceMin != null) {
            products = productService.filterProducts(null, null, priceMin, priceMax, null, null);
        }
        if(size != null) {
            products = productService.filterProducts(null, null, null, null, size, null);
        }

        if(colorId != null) {
             products = productService.filterProducts(null, null, null,null, null, colorId);
        }
        

        model.addAttribute("products", products);
        
        
        return "shopper/product-listing";
    }

    @GetMapping("/product/{id}")
    public String getProductDetailPage(@PathVariable Long id, Model model, HttpServletRequest request) {
        Product product = productService.findById(id);
        
        request.getSession().setAttribute("PRODUCT_ID_BEFORE_LOGIN", id);
        model.addAttribute("product", product);
        // model.addAttribute("categories", categoryService.findAll());
        // model.addAttribute("brands", brandService.findAll());
        // model.addAttribute("sizes", productSizeService.findAll());
        // model.addAttribute("colors", colorService.findAll());
        return "shopper/product";
    }

    
}
