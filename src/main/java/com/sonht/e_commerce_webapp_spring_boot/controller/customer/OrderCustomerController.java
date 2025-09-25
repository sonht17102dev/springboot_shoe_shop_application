package com.sonht.e_commerce_webapp_spring_boot.controller.customer;

import java.util.List;
import java.util.Optional;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.sonht.e_commerce_webapp_spring_boot.dto.OrderWebDto;
import com.sonht.e_commerce_webapp_spring_boot.entity.OrderWeb;
import com.sonht.e_commerce_webapp_spring_boot.service.OrderService;
import com.sonht.e_commerce_webapp_spring_boot.service.UserService;
import com.sonht.e_commerce_webapp_spring_boot.util.Ultilities;

import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;


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
        // validate form
        if (result.hasErrors()) {
            model.addAttribute("orderWebDto", orderWebDto);
            return "shopper/cart";
        }
        if(orderWebDto.getPaymentMethod().equals("ATM") ) {
            Long orderId = Ultilities.mappingDataDtoToEntity(orderWebDto, userService, orderService, true).getId();
            
            return "redirect:/payment/create" + "?amount=" + orderWebDto.getTotalAmount() + "&orderId=" + String.valueOf(orderId);
        }
        OrderWeb orderWeb = Ultilities.mappingDataDtoToEntity(orderWebDto, userService, orderService, false);

        // hiện thị thông tin lên order-result
        model.addAttribute("orderWeb", orderWeb);
        model.addAttribute("result", "Đặt hàng thành công");
        model.addAttribute("note", "Cảm ơn bạn đã mua hàng tại TopShoe");
        model.addAttribute("isSuccess", true);

        return "shopper/order-result";
    }

    @GetMapping("/paybill")
    public String getVNPAYPage() {
        
        return "shopper/vnpay-demo";
    }

    @GetMapping("/order/{orderWebId}")
    public String getMethodName(@PathVariable Long orderWebId, Model model) {
        Optional<OrderWeb> orderWebOp = orderService.findById(orderWebId);
        if(orderWebOp.isPresent()) {
            model.addAttribute("orderWeb", orderWebOp.get());
        }

        return "shopper/order-detail";
    }

    @PostMapping("/cancel-order/{orderWebId}")
    @ResponseBody
    public String handleConfirmOrder(@PathVariable Long orderWebId) {
        orderService.cancelOrder(orderWebId);
        
        return "cancelled";
    }

    
}
