package com.sonht.e_commerce_webapp_spring_boot.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OrderWebDto {
    
    private String consignee;

    private String consigneePhone;

    private String deliveryAddress; 

    private String deliveryStatus; //trạng thái vận chuyển

    private String paymentMethod;

    private String paymentStatus; //trạng thái thanh toán

    private boolean sentMail;

    private Double totalAmount;

    private Long customerId;

    private boolean activeATM;

    private boolean activeCOD;

}
