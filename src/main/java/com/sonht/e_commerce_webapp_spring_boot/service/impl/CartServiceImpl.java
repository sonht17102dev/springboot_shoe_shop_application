package com.sonht.e_commerce_webapp_spring_boot.service.impl;

import org.springframework.stereotype.Service;

import com.sonht.e_commerce_webapp_spring_boot.entity.CartItem;
import com.sonht.e_commerce_webapp_spring_boot.entity.Product;
import com.sonht.e_commerce_webapp_spring_boot.entity.ProductSize;
import com.sonht.e_commerce_webapp_spring_boot.entity.User;
import com.sonht.e_commerce_webapp_spring_boot.service.CartService;
import com.sonht.e_commerce_webapp_spring_boot.service.ProductService;
import com.sonht.e_commerce_webapp_spring_boot.service.ProductSizeService;
import com.sonht.e_commerce_webapp_spring_boot.service.UserService;

@Service
public class CartServiceImpl implements CartService {

    private final UserService userService;
    private final ProductService productService;
    private final ProductSizeService productSizeService;

    public CartServiceImpl(UserService userService, ProductService productService, ProductSizeService productSizeService) {
        this.userService = userService;
        this.productService = productService;
        this.productSizeService = productSizeService;
    }

    @Override
    public void handleAddProductToCart(Long productId, Integer size, Integer quantity,  String email) {
        System.out.println(productId + " " + size + " " + quantity + " " + email);
        User user = userService.findByEmail(email);
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
            
        }

    }

  

}
