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
/*
 * Lớp ProductWishListDto đại diện cho dữ liệu sản phẩm trong danh sách yêu thích
 * với các thuộc tính như sản phẩm và trạng thái yêu thích.
 */
public class ProductWishListDto {
    
    private Product product;
    private boolean isWishlist = true;
}
