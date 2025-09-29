package com.sonht.e_commerce_webapp_spring_boot.controller.customer;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.sonht.e_commerce_webapp_spring_boot.dto.RegistrationDto;
import com.sonht.e_commerce_webapp_spring_boot.entity.User;
import com.sonht.e_commerce_webapp_spring_boot.service.UserService;

import jakarta.validation.Valid;

@Controller
public class AuthenCustomerController {

    private UserService userService;
    
    
    public AuthenCustomerController(UserService userService) {
        this.userService = userService;
    }
    /*
     * Xử lý trang đăng nhập khách hàng
     */
    @GetMapping("/user/login")
    public String userLogin() {

        return "shopper/login";
    }

    /*
     * Xử lý trang đăng ký khách hàng
     */

    @GetMapping("/register")
    public String register(Model model) {
        // this object RegistrationDto  holds form data
        model.addAttribute("user", new RegistrationDto());
        return "shopper/register";
    }

    /*
     * Xử lý đăng ký khách hàng
     */
    @PostMapping("/register-user")
    public String registerUser(@Valid @ModelAttribute("user") RegistrationDto user, BindingResult bindingResult, Model model) {
        // kiểm tra email đã tồn tại chưa
        User userExists = userService.findByEmail(user.getEmail());
        if(userExists != null && userExists.getEmail()!= null && !userExists.getEmail().isEmpty()) {
            bindingResult.rejectValue("email", null, "Email này đã được sử dụng!");
        }
        // validate form
        if(bindingResult.hasErrors()) {
            model.addAttribute("user", user);
            return "shopper/register";
        }
        // lưu user
        userService.saveUser(user);
        return "redirect:/register?success";
    }
}
