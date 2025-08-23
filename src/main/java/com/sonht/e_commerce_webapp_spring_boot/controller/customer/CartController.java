package com.sonht.e_commerce_webapp_spring_boot.controller.customer;

import java.net.http.HttpRequest;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sonht.e_commerce_webapp_spring_boot.entity.CartItem;
import com.sonht.e_commerce_webapp_spring_boot.entity.Product;
import com.sonht.e_commerce_webapp_spring_boot.entity.ProductSize;
import com.sonht.e_commerce_webapp_spring_boot.entity.User;
import com.sonht.e_commerce_webapp_spring_boot.service.ProductService;
import com.sonht.e_commerce_webapp_spring_boot.service.ProductSizeService;
import com.sonht.e_commerce_webapp_spring_boot.service.UserService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

import org.springframework.web.bind.annotation.RequestBody;

@Controller
public class CartController {
    private final ProductService productService;
    private final UserService userService;
    private final ProductSizeService productSizeService;

    public CartController(ProductService productService, UserService userService,
            ProductSizeService productSizeService) {
        this.productService = productService;
        this.userService = userService;
        this.productSizeService = productSizeService;
    }

    @GetMapping("/user/add-to-cart/{productId}")
    public String addToCart(@PathVariable Long productId, HttpServletRequest request, Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();

        User user = userService.findByEmail(username);

        Product product = productService.findById(productId);
        if (user.getCartItems().isEmpty()) {
            CartItem cartItem = new CartItem();
            cartItem.setUser(user);
            ProductSize productSize = productSizeService.findProductSizeByProductAndSize(product, 41);
            cartItem.setProductSize(productSize); // Assuming the first size is selected
            cartItem.setQuantity(1);
            cartItem.setCreatedAt(java.time.LocalDateTime.now());
            cartItem.setUpdatedAt(java.time.LocalDateTime.now());
            user.getCartItems().add(cartItem);
            model.addAttribute("cartItems", user.getCartItems());

        } else {
            boolean productExistsInCart = false;
            for (CartItem cartItem : user.getCartItems()) {
                if (cartItem.getProductSize().getProduct().getId().equals(productId)) {
                    cartItem.setQuantity(cartItem.getQuantity() + 1);
                    cartItem.setUpdatedAt(java.time.LocalDateTime.now());
                    productExistsInCart = true;
                    break;
                }
            }
            if (!productExistsInCart) {
                CartItem newCartItem = new CartItem();
                newCartItem.setUser(user);
                ProductSize productSize = productSizeService.findProductSizeByProductAndSize(product, 41);
                newCartItem.setProductSize(productSize); // Assuming the first size is selected
                newCartItem.setQuantity(1);
                newCartItem.setCreatedAt(java.time.LocalDateTime.now());
                newCartItem.setUpdatedAt(java.time.LocalDateTime.now());
                user.getCartItems().add(newCartItem);
            }
            model.addAttribute("cartItems", user.getCartItems());
        }
        return "shopper/cart"; // Redirect to the cart page after adding the product
    }

    // @PostMapping("/signed-in")
    // @ResponseBody
    // public String checkUserLogin() {

    // return "shopper/cart";
    // }

    // @PostMapping("/add-to-cart-header/{productId}")
    // @ResponseBody
    // public Product postMethodName(@PathVariable Long productId) {
    // Product product = productService.findById(productId);

    // return product;
    // }

    // @PostMapping("/cart-header/{productId}")
    // @ResponseBody
    // public Product postMethodNam1(@PathVariable Long productId) {
    // Product product = productService.findById(productId);

    // return product;
    // }

}
