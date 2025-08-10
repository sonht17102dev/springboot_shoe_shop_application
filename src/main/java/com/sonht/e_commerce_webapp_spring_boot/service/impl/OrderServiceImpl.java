package com.sonht.e_commerce_webapp_spring_boot.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.sonht.e_commerce_webapp_spring_boot.entity.OrderWeb;
import com.sonht.e_commerce_webapp_spring_boot.repository.OrderRepository;
import com.sonht.e_commerce_webapp_spring_boot.service.OrderService;

@Service
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;

    public OrderServiceImpl(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    @Override
    public List<OrderWeb> getAllOrders() {
        return orderRepository.findAll();
    }

    @Override
    public OrderWeb getOrderById(Long orderId) {
        return orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found with id: " + orderId));
    }

    @Override
    public void updateDeliveryStatus(Long orderId, String deliveryStatus) {
        OrderWeb order = getOrderById(orderId);
        order.setDeliveryStatus(deliveryStatus);
        orderRepository.save(order);
    }
    
}
