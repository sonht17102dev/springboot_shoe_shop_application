package com.sonht.e_commerce_webapp_spring_boot.service;

public interface CartService {

    void handleAddProductToCart(Long productId, Integer size, Integer quantity, String username);
}
