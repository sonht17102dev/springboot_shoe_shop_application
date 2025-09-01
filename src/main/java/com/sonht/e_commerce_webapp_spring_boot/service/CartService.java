package com.sonht.e_commerce_webapp_spring_boot.service;

import java.util.List;

import com.sonht.e_commerce_webapp_spring_boot.dto.CartItemDto;
import com.sonht.e_commerce_webapp_spring_boot.entity.CartItem;

public interface CartService {

    void handleAddProductToCart(CartItemDto cartItemRequest,  String username);

    void removeCartItem(Long cartItemId);

    void removeAllCartItems();

    Double calculateTotalPrice(List<CartItem> cartItems);
}
