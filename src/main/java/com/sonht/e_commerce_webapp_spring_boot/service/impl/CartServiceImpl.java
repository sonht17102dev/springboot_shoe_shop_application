package com.sonht.e_commerce_webapp_spring_boot.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.sonht.e_commerce_webapp_spring_boot.dto.CartItemDto;
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
    public void handleAddProductToCart(CartItemDto cartItemRequest, String email) {

        User user = userService.findByEmail(email);
        Product product = productService.findById(cartItemRequest.getProductId());

        List<CartItem> cartItems = user.getCartItems();

        // Kiểm tra giỏ hàng của người dùng có trống không
        if (cartItems.isEmpty()) {
            saveCartItem(user, product, cartItemRequest, cartItems);
        } else { // nếu cart không trống
            // Kiểm tra xem sản phẩm đã tồn tại trong giỏ hàng chưa
            boolean productExistsInCart = false;
            for (CartItem cartItem : user.getCartItems()) {
                if (cartItem.getProductSize().getProduct().getId().equals(cartItemRequest.getProductId())
                        && cartItem.getProductSize().getSize().equals(cartItemRequest.getSize())) {
                    cartItem.setQuantity(cartItem.getQuantity() + 1); // Tăng số lượng sản phẩm lên 1
                    cartItem.setUpdatedAt(java.time.LocalDateTime.now());
                    cartRepository.save(cartItem); // Cập nhật cart item trong database
                    productExistsInCart = true;
                    break;
                } else { // kiểm trả size khác
                    Optional<ProductSize> productSizeOp = productSizeService.findProductSizeByProductAndSize(product,
                            cartItemRequest.getSize());
                    if (productSizeOp.isPresent()) {
                        ProductSize productSize = productSizeOp.get();
                        if (cartItem.getProductSize().getId().equals(productSize.getId())) {
                            cartItem.setQuantity(cartItem.getQuantity() + 1); // Tăng số lượng sản phẩm lên 1
                            cartItem.setUpdatedAt(java.time.LocalDateTime.now());
                            cartRepository.save(cartItem); // Cập nhật cart item trong database
                            productExistsInCart = true;
                            break;
                        }
                    }

                }
            }
           
            if (!productExistsInCart) { // Nếu sản phẩm chưa tồn tại trong giỏ hàng, thêm mới
                saveCartItem(user, product, cartItemRequest, cartItems);
            }
        }

    }

    public void saveCartItem(User user, Product product, CartItemDto cartItemRequest, List<CartItem> cartItems) {

        CartItem cartItem = new CartItem();
        cartItem.setUser(user);

        Optional<ProductSize> productSizeOp = productSizeService.findProductSizeByProductAndSize(product,
                cartItemRequest.getSize());
        if (productSizeOp.isPresent()) {
            ProductSize productSize = productSizeOp.get();
            System.out.println(productSize.getSize());
            cartItem.setProductSize(productSize);
            List<CartItem> cartItemsNew = new ArrayList<>();

            cartItem.setQuantity(1);
            cartItem.setCreatedAt(java.time.LocalDateTime.now());
            cartItem.setUpdatedAt(java.time.LocalDateTime.now());
            cartItems.add(cartItem);
            cartItemsNew.add(cartItem);
            productSize.setCartItems(cartItemsNew);
            productSize.setProduct(product);
            user.setCartItems(cartItems);
            cartRepository.save(cartItem); // lưu cart item vào database
        } else {
            throw new RuntimeException("Product size not found");
        }

    }

    @Override
    public void removeCartItem(Long cartItemId) {
        cartRepository.deleteById(cartItemId);
    }

    @Override
    public void removeAllCartItems() {
        cartRepository.deleteAll();
    }

    @Override
    public Long calculateTotalPrice(List<CartItem> cartItems) {
        return cartItems.stream()
                .mapToLong(item -> (long) (item.getProductSize().getProduct().getPrice() * item.getQuantity()))
                .sum();
    }

}
