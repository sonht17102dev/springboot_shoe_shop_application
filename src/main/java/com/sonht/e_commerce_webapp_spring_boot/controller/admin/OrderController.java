package com.sonht.e_commerce_webapp_spring_boot.controller.admin;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sonht.e_commerce_webapp_spring_boot.dto.Action;
import com.sonht.e_commerce_webapp_spring_boot.dto.OrderDto;
import com.sonht.e_commerce_webapp_spring_boot.entity.OrderWeb;
import com.sonht.e_commerce_webapp_spring_boot.entity.OrderWebDetail;
import com.sonht.e_commerce_webapp_spring_boot.service.OrderService;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

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
    public String getOrderDetailPage(@PathVariable("orderId") Long orderId, Model model) {
        OrderWeb order = orderService.getOrderById(orderId);
        if (order != null) {
            // List<OrderWebDetail> orderDetails = order.getOrderWebDetails();
            model.addAttribute("orderWeb", order);

            switch (order.getDeliveryStatus()) {
                case "unprocessed" -> {
                    model.addAttribute("actions", List.of(
                            new Action("Duyệt đơn hàng này", "btn btn-success", "wait")));
                    model.addAttribute("undoAction", "not_undo");
                    model.addAttribute("hasCancelled", true);
                }
                case "wait" -> {
                    model.addAttribute("actions", List.of(
                            new Action("Bắt đầu giao hàng", "btn btn-primary", "delivery")));
                    model.addAttribute("hasCancelled", true);
                    model.addAttribute("undoAction", "unprocessed");
                }
                case "delivery" -> {
                    model.addAttribute("actions", List.of(

                            new Action("Giao hàng thành công", "btn btn-success", "successful"),
                            new Action("Giao hàng thất bại, chờ giao hàng lần 2", "btn btn-warning", "delivery2")));
                    model.addAttribute("hasCancelled", true);
                    model.addAttribute("undoAction", "wait");
                }
                case "delivery2" -> {
                    model.addAttribute("actions", List.of(

                            new Action("Giao hàng thành công", "btn btn-success", "successful"),
                            new Action("Giao hàng không thành công", "btn btn-warning", "cancel")));
                    model.addAttribute("hasCancelled", true);
                }
                case "successful" -> {
                    model.addAttribute("actions", null);
                    model.addAttribute("undoAction", "not_undo");
                    model.addAttribute("hasCancelled", null);
                }
                case "cancel" -> {
                    // model.addAttribute("hasCancelled", false);
                    model.addAttribute("actions", null);
                    model.addAttribute("undoAction", "not_undo");
                    model.addAttribute("hasCancelled", null);
                }
            }
            ;

            return "admin/order-detail";
        } else {
            model.addAttribute("error", "Order not found");
            return "admin/orders"; // Redirect to orders page if order not found

        }

    }

    @PostMapping("/orders/change-status")
    @ResponseBody
    public String handleChangeStatusOrder(@RequestParam("id") Long orderId,
            @RequestParam("status") String status, Model model) {

        OrderWeb order = orderService.getOrderById(orderId);

        if (order != null) {
            // change status of delivery
            orderService.updateDeliveryStatus(orderId, status);
            // send data to client to rerender
            return "Successfully updated order status";
        }

        return "Order not found or status update failed";
    }
    @PostMapping("/orders/cancel/{orderId}")
    @ResponseBody
    public String handleCancelOrder(@PathVariable("orderId") Long orderId) {
        System.out.println(orderId);
        OrderWeb order = orderService.getOrderById(orderId);
        
        if (order != null) {
            orderService.cancelOrder(orderId);
            return "Successfully cancelled order";
        }
        return "Order not found or cancellation failed";
    }
}
