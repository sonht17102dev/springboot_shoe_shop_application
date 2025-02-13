package com.sonht.e_commerce_webapp_spring_boot.controller.customer;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.sonht.e_commerce_webapp_spring_boot.entity.User;

@Controller
public class AuthenCustomerController {

    @GetMapping("/user/login")
    public String userLogin() {

        return "shopper/login";
    }

    @GetMapping("/user/my-account")
    public String myAccount() {
        return "shopper/account";
    }

    @GetMapping("/register")
    public String register(Model model) {
        model.addAttribute("user", new User());

        return "shopper/register";
    }

    @PostMapping("/register-user")
    public String registerUser(@ModelAttribute("user") User user) {
        System.out.println(user);
        return "shopper/register";
    }
}
