package com.sonht.e_commerce_webapp_spring_boot.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
/*
 * Lớp CartItemDto đại diện cho một mục trong giỏ hàng với các thuộc tính như ID sản phẩm, kích thước và số lượng.
 */
public class CartItemDto {
    private Long productId;
    private Integer size;
    private Integer quantity;
}

