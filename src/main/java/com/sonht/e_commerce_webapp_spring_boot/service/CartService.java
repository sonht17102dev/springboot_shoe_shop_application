package com.sonht.e_commerce_webapp_spring_boot.service;

import com.sonht.e_commerce_webapp_spring_boot.dto.CartItemRequest;
import com.sonht.e_commerce_webapp_spring_boot.entity.CartItem;

public interface CartService {

    void handleAddProductToCart(CartItemRequest cartItemRequest,  String username);

    void removeCartItem(Long cartItemId);

    void removeAllCartItems();
}
