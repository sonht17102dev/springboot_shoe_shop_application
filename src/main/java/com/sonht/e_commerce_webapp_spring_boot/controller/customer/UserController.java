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
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.PostMapping;




@Controller
@RequestMapping("/user")
public class UserController {
    private final UserService userService;
    private final OrderService orderService;

    public UserController(UserService userService, OrderService orderService) {
        this.userService = userService;
        this.orderService = orderService;
    }

    @GetMapping("my-account")
    public String getMyAccountPage(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        model.addAttribute("orderWebs", userService.findByEmail(email).getOrderWebs());
        model.addAttribute("user", userService.findByEmail(email));

        return "shopper/account";
    }

    @GetMapping("my-account/order/{orderWebId}")
    public String getMethodName(@PathVariable Long orderWebId, Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        Optional<OrderWeb> orderWebOp = orderService.findById(orderWebId);
        if(orderWebOp.isPresent()) {
            model.addAttribute("orderWeb", orderWebOp.get());
            model.addAttribute("user", userService.findByEmail(email));
        }

        return "shopper/account_order";
    }
    
    
    
    @GetMapping("my-account/edit")
    public String getEditUserPage() {

        return "shopper/account_address_fields.html";
    }

    @PostMapping("my-account/cancel-order/{orderWebId}")
    @ResponseBody
    public String handleConfirmOrder(@PathVariable Long orderWebId) {
        orderService.cancelOrder(orderWebId);
        
        return "cancelled";
    }
    
    
}
