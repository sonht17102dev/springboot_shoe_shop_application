package com.sonht.e_commerce_webapp_spring_boot.controller.customer;

import java.util.Optional;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.sonht.e_commerce_webapp_spring_boot.entity.OrderWeb;
import com.sonht.e_commerce_webapp_spring_boot.service.OrderService;
import com.sonht.e_commerce_webapp_spring_boot.service.UserService;




@Controller
@RequestMapping("/user")
public class UserController {
    private final UserService userService;
    private final OrderService orderService;

    public UserController(UserService userService, OrderService orderService) {
        this.userService = userService;
        this.orderService = orderService;
    }

    /*
     * Xử lý trang thông tin tài khoản
     */
    @GetMapping("my-account")
    public String getMyAccountPage(Model model) {
        // Lấy thông tin user đã đăng nhập
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        // Hiển thị thông tin user và đơn hàng của user
        model.addAttribute("orderWebs", userService.findByEmail(email).getOrderWebs());
        model.addAttribute("user", userService.findByEmail(email));
        return "shopper/account";
    }

    /*
     * Xử lý trang chi tiết đơn hàng của khách hàng
     */
    @GetMapping("my-account/order/{orderWebId}")
    public String getMethodName(@PathVariable Long orderWebId, Model model) {
        // Lấy thông tin chi tiết đơn hàng theo id
        Optional<OrderWeb> orderWebOp = orderService.findById(orderWebId);
        if(orderWebOp.isPresent()) {
            model.addAttribute("orderWeb", orderWebOp.get());
        }

        return "shopper/order-detail";
    }
    
    
    // @GetMapping("my-account/edit")
    // public String getEditUserPage() {

    //     return "shopper/account_address_fields.html";
    // }

    
    
    
}
