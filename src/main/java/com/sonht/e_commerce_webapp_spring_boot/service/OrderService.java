package com.sonht.e_commerce_webapp_spring_boot.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.sonht.e_commerce_webapp_spring_boot.entity.OrderWeb;

@Service
public interface OrderService {

    List<OrderWeb> getAllOrders();

    OrderWeb getOrderById(Long orderId);

    void updateDeliveryStatus(Long orderId, String deliveryStatus);

    void cancelOrder(Long orderId);
} 
