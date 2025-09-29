package com.sonht.e_commerce_webapp_spring_boot.controller.customer;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.sonht.e_commerce_webapp_spring_boot.entity.Product;
import com.sonht.e_commerce_webapp_spring_boot.service.ProductService;

import jakarta.servlet.http.HttpServletRequest;

@Controller
public class HomeController {

    private final ProductService productService;

    public HomeController(ProductService productService) {
        this.productService = productService;
    }
    /*
     * Xử lý trang chủ
     */
    @GetMapping({ "/", "/index" })
    public String getHomePage(Model model, HttpServletRequest request) {
        // Lấy danh sách sản phẩm đang bán
        List<Product> products = productService.findAllByStatus("Đang bán");
        model.addAttribute("products", products);
        
        return "shopper/index";
    }

    /*
     * Xử lý trang tìm kiếm sản phẩm khi người dùng nhập từ khóa vào thanh tìm kiếm
     */
    @GetMapping("/search")
    public String searchProducts(Model model, @RequestParam("q") String keyword) {
        // Tìm kiếm sản phẩm theo từ khóa
        List<Product> products = productService.searchProducts(keyword);
        model.addAttribute("products", products);
        model.addAttribute("text", keyword);
        return "shopper/search";
    }

    /*
     * Xử lý trang giới thiệu
     */
    @GetMapping("/about")
    public String getAboutPage(Model model) {
        return "shopper/about";
    }
    /*
     * Xử lý trang liên hệ
     */
    @GetMapping("/contact")
    public String getContactPage(Model model) {
        return "shopper/contact";
    }
    

    
}
