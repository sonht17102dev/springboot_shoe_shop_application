package com.sonht.e_commerce_webapp_spring_boot.controller.admin;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.sonht.e_commerce_webapp_spring_boot.dto.Action;
import com.sonht.e_commerce_webapp_spring_boot.dto.OrderDto;
import com.sonht.e_commerce_webapp_spring_boot.entity.OrderWeb;
import com.sonht.e_commerce_webapp_spring_boot.entity.OrderWebDetail;
import com.sonht.e_commerce_webapp_spring_boot.service.OrderService;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;


@Controller
@RequestMapping("admin")
public class OrderController {
    
    private OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }
    
    @GetMapping("/orders")
    public String getOrderPage(Model model) {
        List<OrderWeb> orders = orderService.getAllOrders(); 
        if (!orders.isEmpty()) {
            List<OrderDto> orderDtos = orders.stream()
                .map(order -> new OrderDto(
                        order.getId(),
                        order.getConsignee(),
                        order.getDeliveryStatus(),
                        order.getPaymentMethod(),
                        order.getPaymentStatus(),
                        order.getTotalAmount()))
                .toList();
            model.addAttribute("orders", orderDtos);
        }
        return "admin/orders";
    }
    
    @GetMapping("/orders/detail/{orderId}")
    public String getOrderDetailPage(@PathVariable("orderId") Long orderId, Model model ) {
        OrderWeb order = orderService.getOrderById(orderId);
        if(order != null) {
            // List<OrderWebDetail> orderDetails = order.getOrderWebDetails();
            model.addAttribute("orderWeb", order);
            switch (order.getDeliveryStatus()) {
                case "unprocessed" -> model.addAttribute("actions", List.of(
                    new Action("Duyệt đơn hàng này", "btn btn-success", "cancel")
                ));
                case "wait" -> model.addAttribute("actions", List.of(
                    new Action("Bắt đầu giao hàng", "btn btn-primary", "confirm")
                ));
                case "delivery" -> model.addAttribute("actions", List.of(
                    
                    new Action("Giao hàng thành công", "btn btn-success", "cancel"),
                    new Action("Giao hàng thất bại, chờ giao hàng lần 2", "btn btn-warning", "cancel")
                ));
                case "delivery2" -> model.addAttribute("actions", List.of(
                    
                    new Action("Giao hàng thành công", "btn btn-success", "cancel"),
                    new Action("Giao hàng không thành công", "btn btn-warning", "cancel")
                ));
                case "successful" -> model.addAttribute("actions", List.of(
                    
                    new Action("Giao hàng thành công", "btn btn-success", "cancel"),
                    new Action("Giao hàng không thành công", "btn btn-warning", "cancel")
                ));
            };
            
            return "admin/order-detail";
        } else {
            model.addAttribute("error", "Order not found");
            return "admin/orders"; // Redirect to orders page if order not found

        }
        
    }
}
