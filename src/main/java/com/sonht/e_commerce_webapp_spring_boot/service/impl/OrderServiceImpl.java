package com.sonht.e_commerce_webapp_spring_boot.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.sonht.e_commerce_webapp_spring_boot.entity.CartItem;
import com.sonht.e_commerce_webapp_spring_boot.entity.OrderWeb;
import com.sonht.e_commerce_webapp_spring_boot.entity.OrderWebDetail;
import com.sonht.e_commerce_webapp_spring_boot.entity.ProductSize;
import com.sonht.e_commerce_webapp_spring_boot.repository.OrderRepository;
import com.sonht.e_commerce_webapp_spring_boot.service.OrderDetailService;
import com.sonht.e_commerce_webapp_spring_boot.service.OrderService;
import com.sonht.e_commerce_webapp_spring_boot.service.ProductSizeService;

@Service
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final OrderDetailService orderWebDetailService;
    private final ProductSizeService productSizeService;

    public OrderServiceImpl(OrderRepository orderRepository, OrderDetailService orderWebDetailService, ProductSizeService productSizeService) {
        this.orderRepository = orderRepository;
        this.orderWebDetailService = orderWebDetailService;
        this.productSizeService = productSizeService;
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

    @Override
    public void cancelOrder(Long orderId) {
        OrderWeb order = getOrderById(orderId);
        order.setDeliveryStatus("cancel");
        orderRepository.save(order);
    }

    @Override
    public void saveOrder(OrderWeb orderWeb) {
        orderRepository.save(orderWeb);
    }

    @Override
    public List<OrderWebDetail> convertCartItemsToOrderDetails(List<CartItem> cartItems,  OrderWeb orderWeb) {
        return cartItems.stream().map(cartItem -> {
            OrderWebDetail orderWebDetail = new OrderWebDetail();
            orderWebDetail.setPrice(cartItem.getProductSize().getProduct().getPrice());
            orderWebDetail.setQuantity(cartItem.getQuantity());
            Optional<ProductSize> productSizeOp = productSizeService.findById(cartItem.getProductSize().getId());
            if (productSizeOp.isPresent()) {
                orderWebDetail.setProductSize(productSizeOp.get());
            }
            orderWebDetail.setProductSize(cartItem.getProductSize());
            orderWebDetail.setCreatedAt(java.time.LocalDateTime.now());
            orderWebDetail.setUpdatedAt(java.time.LocalDateTime.now());
            Long totalAmountOrderWebDetail = (Long) (cartItem.getQuantity() * cartItem.getProductSize().getProduct().getPrice());
            orderWebDetail.setTotalAmount(totalAmountOrderWebDetail);
            orderWebDetail.setOrderWeb(orderWeb);
            orderWebDetailService.saveOrderWebDetail(orderWebDetail);
            return orderWebDetail;
        }).toList();
    }

    @Override
    public Optional<OrderWeb> findById(Long orderWebId) {
        return orderRepository.findById(orderWebId);
    }

}
