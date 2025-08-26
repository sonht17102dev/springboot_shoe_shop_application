package com.sonht.e_commerce_webapp_spring_boot.controller.customer;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.sonht.e_commerce_webapp_spring_boot.dto.CartItemRequest;
import com.sonht.e_commerce_webapp_spring_boot.entity.User;
import com.sonht.e_commerce_webapp_spring_boot.service.CartService;
import com.sonht.e_commerce_webapp_spring_boot.service.UserService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class CartController {
    private final CartService cartService;
    private final UserService userService;

    public CartController(CartService cartService, UserService userService) {
        this.cartService = cartService;
        this.userService = userService;
    }

    @PostMapping("/saveItem")
    @ResponseBody
    public String saveItemToSession(HttpServletRequest request, @RequestBody CartItemRequest cartItemRequest) {
        HttpSession session = request.getSession();

        session.setAttribute("cartItemRequest", cartItemRequest);
        
        return "Thêm sản phẩm thành công"; // Redirect to the cart page
    }

    @GetMapping("/user/cart")
    public String showCart(Model model, HttpServletRequest request) {
        // Get the current user's from session
        HttpSession session = request.getSession();
        String username = (String) session.getAttribute("username");
        CartItemRequest cartItemRequest=  (CartItemRequest) session.getAttribute("cartItemRequest");
        // System.out.println(cartItemRequest);
        if(username == null || cartItemRequest == null) {
            return "redirect:/user/login"; // Redirect to login if not authenticated
        }
        cartService.handleAddProductToCart(cartItemRequest, username);

        // Fetch the user and their cart items
        User user = userService.findByEmail(username);
        model.addAttribute("cartItems", user.getCartItems());

        return "shopper/cart"; // Return the cart view
    }


}
