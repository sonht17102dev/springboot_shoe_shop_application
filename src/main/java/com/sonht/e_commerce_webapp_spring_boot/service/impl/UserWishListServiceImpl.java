package com.sonht.e_commerce_webapp_spring_boot.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sonht.e_commerce_webapp_spring_boot.entity.UserWishlist;
import com.sonht.e_commerce_webapp_spring_boot.repository.UserWishListRepository;
import com.sonht.e_commerce_webapp_spring_boot.service.ProductService;
import com.sonht.e_commerce_webapp_spring_boot.service.UserService;
import com.sonht.e_commerce_webapp_spring_boot.service.UserWishListService;

@Service
public class UserWishListServiceImpl implements UserWishListService {

    private final UserWishListRepository userWishListRepository;
    private final UserService userService;
    private final ProductService productService;
    
    
    public UserWishListServiceImpl(UserWishListRepository userWishListRepository, ProductService productService, UserService userService) {
        this.userWishListRepository = userWishListRepository;
        this.userService = userService;
        this.productService = productService;
    }

    /*
     * Thêm hoặc xóa sản phẩm khỏi danh sách yêu thích của người dùng
     */
    @Override
    @Transactional
    public boolean toggleWishlist(long userId, Long productId) {
        var wishlistOpt = userWishListRepository.findByCustomerIdAndProductId(userId, productId);
        if (wishlistOpt.isPresent()) {
            // Nếu đã tồn tại thì xóa
            userWishListRepository.deleteByCustomerIdAndProductId(userId, productId);
            return false; // trạng thái sau khi xử lý = yêu thích
        } else {
            // Nếu chưa có thì thêm mới
            var user = userService.findById(userId).get();
            var product = productService.findById(productId);

            UserWishlist userWishlist = new UserWishlist();
            userWishlist.setCustomer(user);
            userWishlist.setProduct(product);
            userWishlist.setCreatedAt(java.time.LocalDateTime.now());
            userWishlist.setUpdatedAt(java.time.LocalDateTime.now());
            userWishListRepository.save(userWishlist);
            return true; // trạng thái sau khi xử lý = đã thích
        }
    }

    /*
     * Lấy tất cả sản phẩm trong danh sách yêu thích
     */
    @Override
    public List<UserWishlist> findAllWishlistProducts() {
        return userWishListRepository.findAll();
    }
    
}
