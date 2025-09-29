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
import com.sonht.e_commerce_webapp_spring_boot.service.OrderService;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequestMapping("admin")
public class OrderController {

    private OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    /*
     * Xử lý trang danh sách đơn hàng
     */
    @GetMapping("/orders")
    public String getOrderPage(Model model) {
        List<OrderWeb> orders = orderService.getAllOrders();
        if (!orders.isEmpty()) {
            // chuyển đổi danh sách OrderWeb thành danh sách OrderDto
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

    /*
     * Xử lý trang chi tiết đơn hàng
     */
    @GetMapping("/orders/detail/{orderId}")
    public String getOrderDetailPage(@PathVariable("orderId") Long orderId, Model model) {
        OrderWeb order = orderService.getOrderById(orderId);
        if (order != null) {
            
            model.addAttribute("orderWeb", order);
            // tùy vào trạng thái đơn hàng để hiển thị các nút hành động tương ứng
            switch (order.getDeliveryStatus()) {
                // unprocessed -> Chưa xét duyệt
                case "unprocessed" -> { 
                    model.addAttribute("actions", List.of(
                            new Action("Duyệt đơn hàng này", "btn btn-success", "wait")));
                    model.addAttribute("undoAction", "not_undo");
                    model.addAttribute("hasCancelled", true);
                }

                // wait -> Chờ giao hàng
                case "wait" -> {
                    model.addAttribute("actions", List.of(
                            new Action("Bắt đầu giao hàng", "btn btn-primary", "delivery")));
                    model.addAttribute("hasCancelled", true);
                    model.addAttribute("undoAction", "unprocessed");
                }
                // delivery -> Đang giao hàng
                case "delivery" -> {
                    model.addAttribute("actions", List.of(

                            new Action("Giao hàng thành công", "btn btn-success", "successful"),
                            new Action("Giao hàng thất bại, chờ giao hàng lần 2", "btn btn-warning", "delivery2")));
                    model.addAttribute("hasCancelled", true);
                    model.addAttribute("undoAction", "wait");
                }

                // delivery2 -> Giao hàng lần 2
                case "delivery2" -> {
                    model.addAttribute("actions", List.of(

                            new Action("Giao hàng thành công", "btn btn-success", "successful"),
                            new Action("Giao hàng không thành công", "btn btn-warning", "cancel")));
                    model.addAttribute("hasCancelled", true);
                }

                // successful -> Giao hàng thành công
                case "successful" -> {
                    model.addAttribute("actions", null);
                    model.addAttribute("undoAction", "not_undo");
                    model.addAttribute("hasCancelled", null);
                }

                // cancel -> Đơn hàng bị hủy
                case "cancel" -> {
                    // model.addAttribute("hasCancelled", false);
                    model.addAttribute("actions", null);
                    model.addAttribute("undoAction", "not_undo");
                    model.addAttribute("hasCancelled", null);
                }
            };

            return "admin/order-detail";
        } else {
            model.addAttribute("error", "Order not found");
            return "admin/orders"; // chuyển hướng về trang danh sách đơn hàng nếu không tìm thấy

        }

    }

    @PostMapping("/orders/change-status")
    @ResponseBody
    public String handleChangeStatusOrder(@RequestParam("id") Long orderId,
            @RequestParam("status") String status, Model model) {
        // Lấy order từ database
        OrderWeb order = orderService.getOrderById(orderId);

        if (order != null) {
            // thay đổi trạng thái đơn hàng
            orderService.updateDeliveryStatus(orderId, status);
            // gửi thông báo về client
            return "Successfully updated order status";
        }

        return "Order not found or status update failed";
    }
    @PostMapping("/orders/cancel/{orderId}")
    @ResponseBody
    public String handleCancelOrder(@PathVariable("orderId") Long orderId) {
        // Lấy order từ database
        OrderWeb order = orderService.getOrderById(orderId);
        
        if (order != null) {
            // hủy đơn hàng
            orderService.cancelOrder(orderId);
            return "Successfully cancelled order";
        }
        return "Order not found or cancellation failed";
    }
}
