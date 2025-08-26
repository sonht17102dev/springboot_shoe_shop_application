package com.sonht.e_commerce_webapp_spring_boot.controller.customer;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.sonht.e_commerce_webapp_spring_boot.service.UserService;


@Controller
@RequestMapping("/user")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("my-account")
    public String getMyAccountPage(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        model.addAttribute("orderWebs", userService.findByEmail(email).getOrderWebs());
        model.addAttribute("user", userService.findByEmail(email));

        return "shopper/account";
    }
    
}
