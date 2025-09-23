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
import com.sonht.e_commerce_webapp_spring_boot.entity.OrderWebDetail;
import com.sonht.e_commerce_webapp_spring_boot.entity.User;
import com.sonht.e_commerce_webapp_spring_boot.service.OrderService;
import com.sonht.e_commerce_webapp_spring_boot.service.UserService;

import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
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
            return "redirect:/payment/create" + "?amount=" + orderWebDto.getTotalAmount() ;
        }
        // Tạo mới orderWeb từ orderWebDto
        OrderWeb orderWeb = new OrderWeb();
        orderWeb.setConsignee(orderWebDto.getConsignee());
        orderWeb.setConsigneePhone(orderWebDto.getConsigneePhone());
        orderWeb.setDeliveryAddress(orderWebDto.getDeliveryAddress());
        orderWeb.setPaymentMethod(orderWebDto.getPaymentMethod());
        orderWeb.setTotalAmount(orderWebDto.getTotalAmount());
        orderWeb.setDeliveryStatus("unprocessed");
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
