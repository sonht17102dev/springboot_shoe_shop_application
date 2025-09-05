package com.sonht.e_commerce_webapp_spring_boot.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.sonht.e_commerce_webapp_spring_boot.entity.CartItem;
import com.sonht.e_commerce_webapp_spring_boot.entity.OrderWeb;
import com.sonht.e_commerce_webapp_spring_boot.entity.OrderWebDetail;

@Service
public interface OrderService {

    List<OrderWeb> getAllOrders();

    OrderWeb getOrderById(Long orderId);

    void updateDeliveryStatus(Long orderId, String deliveryStatus);

    void cancelOrder(Long orderId);

    void saveOrder(OrderWeb orderWeb);

    List<OrderWebDetail> convertCartItemsToOrderDetails(List<CartItem> cartItems, OrderWeb orderWeb);

    Optional<OrderWeb> findById(Long orderWebId);
} 
