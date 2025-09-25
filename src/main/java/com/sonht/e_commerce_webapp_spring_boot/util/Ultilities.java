package com.sonht.e_commerce_webapp_spring_boot.util;

import java.util.List;
import java.util.Optional;

import com.sonht.e_commerce_webapp_spring_boot.dto.OrderWebDto;
import com.sonht.e_commerce_webapp_spring_boot.entity.OrderWeb;
import com.sonht.e_commerce_webapp_spring_boot.entity.OrderWebDetail;
import com.sonht.e_commerce_webapp_spring_boot.entity.User;
import com.sonht.e_commerce_webapp_spring_boot.service.OrderService;
import com.sonht.e_commerce_webapp_spring_boot.service.UserService;

public class Ultilities {
    public static OrderWeb mappingDataDtoToEntity(OrderWebDto orderWebDto, UserService userService, OrderService orderService, boolean isATM) {
        // Tạo mới orderWeb từ orderWebDto
        OrderWeb orderWeb = new OrderWeb();
        orderWeb.setConsignee(orderWebDto.getConsignee());
        orderWeb.setConsigneePhone(orderWebDto.getConsigneePhone());
        orderWeb.setDeliveryAddress(orderWebDto.getDeliveryAddress());
        orderWeb.setPaymentMethod(orderWebDto.getPaymentMethod());
        orderWeb.setTotalAmount(orderWebDto.getTotalAmount());
        orderWeb.setDeliveryStatus("unprocessed");
        if(isATM)
            orderWeb.setPaymentStatus("Đã thanh toán");
        else
            orderWeb.setPaymentStatus("Chưa thanh toán");
        orderWeb.setSentMail(false);
        // Lấy thông tin user từ customerId và gán vào orderWeb
        Optional<User> user = userService.findById(orderWebDto.getCustomerId());
        if (user.isPresent()) {
            orderWeb.setUser(user.get());
        }
        // Lưu orderWeb và các orderWebDetails
        orderService.saveOrder(orderWeb);
        // mapping dữ liệu từ cartItems sang orderWebDetails
        List<OrderWebDetail> orderWebDetails = orderService.convertCartItemsToOrderDetails(user.get().getCartItems(), orderWeb);
        orderWeb.setOrderWebDetails(orderWebDetails);
        orderWeb.setCreatedAt(java.time.LocalDateTime.now());
        orderWeb.setUpdatedAt(java.time.LocalDateTime.now());

        return orderWeb;
    }

    public static String getFormatId(String id) {
        return "TS" + String.format("%s", id);
    }
}
