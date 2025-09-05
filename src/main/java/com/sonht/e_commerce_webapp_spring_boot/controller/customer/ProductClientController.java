package com.sonht.e_commerce_webapp_spring_boot.controller.customer;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sonht.e_commerce_webapp_spring_boot.dto.ProductWishListDto;
import com.sonht.e_commerce_webapp_spring_boot.entity.Product;
import com.sonht.e_commerce_webapp_spring_boot.entity.User;
import com.sonht.e_commerce_webapp_spring_boot.service.BrandService;
import com.sonht.e_commerce_webapp_spring_boot.service.CategoryService;
import com.sonht.e_commerce_webapp_spring_boot.service.ColorService;
import com.sonht.e_commerce_webapp_spring_boot.service.ProductService;
import com.sonht.e_commerce_webapp_spring_boot.service.ProductSizeService;
import com.sonht.e_commerce_webapp_spring_boot.service.UserService;
import com.sonht.e_commerce_webapp_spring_boot.service.UserWishListService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class ProductClientController {
    private final CategoryService categoryService;
    private final BrandService brandService;
    private final ProductSizeService productSizeService;
    private final ColorService colorService;
    private final ProductService productService;
    private final UserWishListService userWishlistService;
    private final UserService userService;

    public ProductClientController(CategoryService categoryService, BrandService brandService,
            ProductSizeService productSizeService, ColorService colorService, ProductService productService,
            UserWishListService userWishlistService, UserService userService) {
        this.categoryService = categoryService;
        this.brandService = brandService;
        this.productSizeService = productSizeService;
        this.colorService = colorService;
        this.productService = productService;
        this.userWishlistService = userWishlistService;
        this.userService = userService;
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
        if (categoryId == null && brandId == null && priceMin == null && priceMax == null && size == null
                && colorId == null) {
            products = productService.findAllByStatus("Đang bán");
        }
        if (categoryId != null) {
            products = productService.filterProducts(categoryId, null, null, null, null, null);
        }
        if (brandId != null) {
            products = brandService.findProductsByBrandId(brandId);
        }
        if (priceMax != null) {
            products = productService.filterProducts(null, null, null, priceMax, null, null);
        }
        if (priceMax != null && priceMin != null) {
            products = productService.filterProducts(null, null, priceMin, priceMax, null, null);
        }
        if (size != null) {
            products = productService.filterProducts(null, null, null, null, size, null);
        }

        if (colorId != null) {
            products = productService.filterProducts(null, null, null, null, null, colorId);
        }

        model.addAttribute("products", products);

        return "shopper/product-listing";
    }

    @GetMapping("/product/{id}")
    public String getProductDetailPage(@PathVariable Long id, Model model, HttpServletRequest request) {
        Product product = productService.findById(id);
        ProductWishListDto productWishListDto = new ProductWishListDto();
        productWishListDto.setProduct(product);
        productWishListDto.setWishlist(false);
        model.addAttribute("product", productWishListDto.getProduct());
        // Lấy thông tin người dùng đã đăng nhập
        HttpSession session = request.getSession();
        String username = (String) session.getAttribute("username");
        if (username == null) {
            model.addAttribute("isSignedIn", false);
            return "shopper/product";
        }
        model.addAttribute("username", username);
        model.addAttribute("isSignedIn", true);
        model.addAttribute("isWishlist", productWishListDto.isWishlist());

        return "shopper/product";
    }

    

}
