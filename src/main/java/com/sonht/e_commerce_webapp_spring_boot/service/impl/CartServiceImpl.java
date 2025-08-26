package com.sonht.e_commerce_webapp_spring_boot.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.sonht.e_commerce_webapp_spring_boot.dto.CartItemRequest;
import com.sonht.e_commerce_webapp_spring_boot.entity.CartItem;
import com.sonht.e_commerce_webapp_spring_boot.entity.Product;
import com.sonht.e_commerce_webapp_spring_boot.entity.ProductSize;
import com.sonht.e_commerce_webapp_spring_boot.entity.User;
import com.sonht.e_commerce_webapp_spring_boot.repository.CartRepository;
import com.sonht.e_commerce_webapp_spring_boot.service.CartService;
import com.sonht.e_commerce_webapp_spring_boot.service.ProductService;
import com.sonht.e_commerce_webapp_spring_boot.service.ProductSizeService;
import com.sonht.e_commerce_webapp_spring_boot.service.UserService;

@Service
public class CartServiceImpl implements CartService {

    private final UserService userService;
    private final ProductService productService;
    private final ProductSizeService productSizeService;
    private final CartRepository cartRepository;

    public CartServiceImpl(UserService userService, ProductService productService,
            ProductSizeService productSizeService, CartRepository cartRepository) {
        this.userService = userService;
        this.productService = productService;
        this.productSizeService = productSizeService;
        this.cartRepository = cartRepository;
    }

    @Override
    public void handleAddProductToCart(CartItemRequest cartItemRequest, String email) {

        User user = userService.findByEmail(email);
        Product product = productService.findById(cartItemRequest.getProductId());
        List<CartItem> cartItems = user.getCartItems();
        
        // Kiểm tra giỏ hàng của người dùng có trống không
        if (cartItems.isEmpty()) {
            CartItem cartItem = new CartItem();
            cartItem.setUser(user);

            ProductSize productSize = productSizeService.findProductSizeByProductAndSize(product,
                    cartItemRequest.getSize());
            cartItem.setProductSize(productSize); // Giả sử kích thước đầu tiên được chọn
            cartItem.setQuantity(1);
            cartItem.setCreatedAt(java.time.LocalDateTime.now());
            cartItem.setUpdatedAt(java.time.LocalDateTime.now());
            cartItems.add(cartItem);
            user.setCartItems(cartItems);
            cartRepository.save(cartItem); // lưu cart item vào database

        } else { // nếu không trống
            // Kiểm tra xem sản phẩm đã tồn tại trong giỏ hàng chưa
            boolean productExistsInCart = false;
            for (CartItem cartItem : user.getCartItems()) {
                if (cartItem.getProductSize().getProduct().getId().equals(cartItemRequest.getProductId())) {
                    cartItem.setQuantity(cartItem.getQuantity() + 1);
                    cartItem.setUpdatedAt(java.time.LocalDateTime.now());
                    productExistsInCart = true;
                    break;
                }
            }
            if (!productExistsInCart) { // Nếu sản phẩm chưa tồn tại trong giỏ hàng, thêm mới
                CartItem newCartItem = new CartItem();
                newCartItem.setUser(user);
                ProductSize productSize = productSizeService.findProductSizeByProductAndSize(product,
                        cartItemRequest.getSize());
                newCartItem.setProductSize(productSize); // Giả sử kích thước đầu tiên được chọn
                newCartItem.setQuantity(1);
                newCartItem.setCreatedAt(java.time.LocalDateTime.now());
                newCartItem.setUpdatedAt(java.time.LocalDateTime.now());
                cartItems.add(newCartItem);
                user.setCartItems(cartItems);
                cartRepository.save(newCartItem); // lưu cart item vào database
            }
        }

    }


}
