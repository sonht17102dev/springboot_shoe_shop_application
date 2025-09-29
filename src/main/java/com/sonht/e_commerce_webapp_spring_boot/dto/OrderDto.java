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
 * Lớp OrderDto đại diện cho dữ liệu đơn hàng với các thuộc tính như ID, tên người nhận, 
 * trạng thái vận chuyển, phương thức thanh toán, trạng thái thanh toán và tổng số tiền đã định dạng.
 */
public class OrderDto {

    private Long id;

    private String consignee;

    private String deliveryStatus; //trạng thái vận chuyển

    private String paymentMethod;

    private String paymentStatus;//trạng thái thanh toán

    private Long formatTotalAmount;

    
  
}
