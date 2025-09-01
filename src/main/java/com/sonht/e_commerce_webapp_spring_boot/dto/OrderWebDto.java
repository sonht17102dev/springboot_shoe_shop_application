package com.sonht.e_commerce_webapp_spring_boot.dto;


import java.util.ArrayList;
import java.util.List;

import com.sonht.e_commerce_webapp_spring_boot.entity.CartItem;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OrderWebDto {
    
    @NotNull
    @NotEmpty(message = "Tên người nhận hàng không được để trống")
    private String consignee;

    @NotNull
    @NotEmpty(message = "Số điện thoại không được để trống")
    private String consigneePhone;

    @NotNull
    @NotEmpty(message = "Địa chỉ không được để trống")
    private String deliveryAddress; 

    private String paymentMethod;

    private Long totalAmount;

    private Long customerId; 


}
