package com.sonht.e_commerce_webapp_spring_boot.controller.customer;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

import com.sonht.e_commerce_webapp_spring_boot.dto.ProductToCartDto;
import com.sonht.e_commerce_webapp_spring_boot.entity.User;
import com.sonht.e_commerce_webapp_spring_boot.service.CartService;
import com.sonht.e_commerce_webapp_spring_boot.service.ProductService;
import com.sonht.e_commerce_webapp_spring_boot.service.ProductSizeService;
import com.sonht.e_commerce_webapp_spring_boot.service.UserService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class CartController {
    private final ProductService productService;
    private final CartService cartService;
    private final ProductSizeService productSizeService;
    private final UserService userService;

    public CartController(ProductService productService, CartService cartService,
            ProductSizeService productSizeService, UserService userService) {
        this.productService = productService;
        this.cartService = cartService;
        this.productSizeService = productSizeService;
        this.userService = userService;
    }

    @PostMapping("/user/add-to-cart")
    public String addToCart(@ModelAttribute("productToCart") ProductToCartDto productToCartDto,
            HttpServletRequest request, Model model) {
        // Get the current user's authentication details
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        Long productId = productToCartDto.getProductId();
        Integer size = productToCartDto.getSize();
        Integer quantity = productToCartDto.getQuantity();
        System.out.println("Product ID: " + productId);
        System.out.println("Size ID: " + size);
        System.out.println("Quantity: " + quantity);
        System.out.println("chạy vào đây");

        cartService.handleAddProductToCart(productId, size, quantity, username);

        return "redirect:/user/cart"; // Redirect to the cart page
    }

    @GetMapping("/user/cart")
    public String showCart(Model model, HttpServletRequest request) {
        // Get the current user's authentication details
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        HttpSession session = request.getSession();
        ProductToCartDto pCartDto =  (ProductToCartDto) session.getAttribute("DATA_BEFORE_LOGIN");
        if(pCartDto != null) {
            System.out.println("Product ID from session: " + pCartDto.getProductId());
            System.out.println("Size from session: " + pCartDto.getSize());
            System.out.println("Quantity from session: " + pCartDto.getQuantity());
            cartService.handleAddProductToCart(pCartDto.getProductId(), pCartDto.getSize(), pCartDto.getQuantity(), username);
        
            session.removeAttribute("DATA_BEFORE_LOGIN");
        }
        // Fetch the user and their cart items
        User user = userService.findByEmail(username);
        model.addAttribute("cartItems", user.getCartItems());

        return "shopper/cart"; // Return the cart view
    }


}
