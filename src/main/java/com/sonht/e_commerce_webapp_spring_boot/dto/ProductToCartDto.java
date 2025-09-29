package com.sonht.e_commerce_webapp_spring_boot.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
/*
 * Lớp ProductToCartDto đại diện cho dữ liệu sản phẩm được thêm vào giỏ hàng với các thuộc tính như ID sản phẩm, 
 * kích thước và số lượng (mặc định là 1).
 */
public class ProductToCartDto {
    private Long productId;
    private Integer size;
    private Integer quantity = 1;   
}
