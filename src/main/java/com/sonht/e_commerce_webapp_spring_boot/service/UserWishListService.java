package com.sonht.e_commerce_webapp_spring_boot.service;

import java.util.List;

import com.sonht.e_commerce_webapp_spring_boot.entity.UserWishlist;

public interface UserWishListService {

    boolean  toggleWishlist(long userId, Long productId);

    List<UserWishlist> findAllWishlistProducts();

}
