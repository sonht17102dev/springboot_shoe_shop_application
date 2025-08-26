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
public class ProductToCartDto {
    private Long productId;
    private Integer size;
    private Integer quantity = 1;   
}
