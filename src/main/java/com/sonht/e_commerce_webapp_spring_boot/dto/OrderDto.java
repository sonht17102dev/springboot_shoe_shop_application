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
public class OrderDto {

    private Long id;

    private String consignee;

    private String deliveryStatus; //trạng thái vận chuyển

    private String paymentMethod;

    private String paymentStatus;//trạng thái thanh toán

    private Long formatTotalAmount;

    
  
}
