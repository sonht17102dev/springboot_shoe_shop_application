package com.sonht.e_commerce_webapp_spring_boot.controller.customer;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.sonht.e_commerce_webapp_spring_boot.dto.OrderWebDto;
import com.sonht.e_commerce_webapp_spring_boot.entity.CartItem;
import com.sonht.e_commerce_webapp_spring_boot.entity.OrderWeb;
import com.sonht.e_commerce_webapp_spring_boot.entity.OrderWebDetail;
import com.sonht.e_commerce_webapp_spring_boot.entity.User;
import com.sonht.e_commerce_webapp_spring_boot.service.OrderService;
import com.sonht.e_commerce_webapp_spring_boot.service.UserService;

import jakarta.validation.Valid;

@Controller
public class OrderCustomerController {

    private UserService userService;
    private OrderService orderService;

    public OrderCustomerController(UserService userService, OrderService orderService) {
        this.userService = userService;
        this.orderService = orderService;
    }

    @PostMapping("/checkout")
    public String handleOrder(@Valid @ModelAttribute OrderWebDto orderWebDto, BindingResult result, Model model) {

        if (result.hasErrors()) {
            model.addAttribute("orderWebDto", orderWebDto);
            return "shopper/cart";
        }
        OrderWeb orderWeb = new OrderWeb();
        orderWeb.setConsignee(orderWebDto.getConsignee());
        orderWeb.setConsigneePhone(orderWebDto.getConsigneePhone());
        orderWeb.setDeliveryAddress(orderWebDto.getDeliveryAddress());
        orderWeb.setPaymentMethod(orderWebDto.getPaymentMethod());
        orderWeb.setTotalAmount(orderWebDto.getTotalAmount());
        orderWeb.setDeliveryStatus("unprocessed");
        orderWeb.setPaymentStatus("Chưa thanh toán");
        orderWeb.setSentMail(false);
        Optional<User> user = userService.findById(orderWebDto.getCustomerId());
        if(user.isPresent()){
            orderWeb.setUser(user.get());   
        }
        List<OrderWebDetail> orderWebDetails = orderService.convertCartItemsToOrderDetails(user.get().getCartItems(), orderWebDto.getTotalAmount(), orderWeb);
        orderWeb.setOrderWebDetails(orderWebDetails);
        
        orderService.saveOrder(orderWeb);
        model.addAttribute("orderWeb", orderWeb);
        model.addAttribute("result", "Đặt hàng thành công");
        model.addAttribute("note", "Cảm ơn bạn đã mua hàng tại TopShoe");
        model.addAttribute("isSuccess", true);

        return "shopper/order-result";
    }

}
