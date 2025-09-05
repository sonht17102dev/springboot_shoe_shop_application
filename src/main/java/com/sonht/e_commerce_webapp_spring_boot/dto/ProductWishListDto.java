package com.sonht.e_commerce_webapp_spring_boot.dto;

import com.sonht.e_commerce_webapp_spring_boot.entity.Product;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProductWishListDto {
    
    private Product product;
    private boolean isWishlist = true;
}
