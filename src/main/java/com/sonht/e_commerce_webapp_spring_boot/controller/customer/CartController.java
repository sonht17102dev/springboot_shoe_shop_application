package com.sonht.e_commerce_webapp_spring_boot.controller.customer;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.sonht.e_commerce_webapp_spring_boot.dto.CartItemRequest;
import com.sonht.e_commerce_webapp_spring_boot.entity.User;
import com.sonht.e_commerce_webapp_spring_boot.service.CartService;
import com.sonht.e_commerce_webapp_spring_boot.service.ProductService;
import com.sonht.e_commerce_webapp_spring_boot.service.ProductSizeService;
import com.sonht.e_commerce_webapp_spring_boot.service.UserService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

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

    @PostMapping("/saveItem")
    @ResponseBody
    public String saveItemToSession(HttpServletRequest request, @RequestBody CartItemRequest cartItemRequest) {
        HttpSession session = request.getSession();

        session.setAttribute("cartItemRequest", cartItemRequest);
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(authentication != null) {
            String username = authentication.getName();
            return username;
        }
        
        return ""; // Redirect to the cart page
    }

    @GetMapping("/user/cart")
    public String showCart(Model model, HttpServletRequest request) {
        // Get the current user's authentication details
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        System.out.println(username + "in user/cart" );
        HttpSession session = request.getSession();
        CartItemRequest cartItemRequest=  (CartItemRequest) session.getAttribute("cartItemRequest");
        // System.out.println(cartItemRequest);
        cartService.handleAddProductToCart(cartItemRequest, username);

        // Fetch the user and their cart items
        User user = userService.findByEmail(username);
        model.addAttribute("cartItems", user.getCartItems());

        return "shopper/cart"; // Return the cart view
    }


}
