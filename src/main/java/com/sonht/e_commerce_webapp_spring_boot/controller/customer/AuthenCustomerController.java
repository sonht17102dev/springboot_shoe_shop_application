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

// @Controller
// public class AuthenCustomerController {

//     private UserService userService;
    
    
//     public AuthenCustomerController(UserService userService) {
//         this.userService = userService;
//     }

//     @GetMapping("/user/login")
//     public String userLogin() {

//         return "shopper/login";
//     }

//     @GetMapping("/user/my-account")
//     public String myAccount() {
//         return "shopper/account";
//     }

//     @GetMapping("/register")
//     public String register(Model model) {
//         // this object RegistrationDto  holds form data
//         model.addAttribute("user", new RegistrationDto());
//         return "shopper/register";
//     }

//     // handler method to handle user registration form submit request
//     @PostMapping("/register-user")
//     public String registerUser(@Valid @ModelAttribute("user") RegistrationDto user, BindingResult bindingResult, Model model) {
//         User userExists = userService.findByEmail(user.getEmail());
//         if(userExists != null && userExists.getEmail()!= null && !userExists.getEmail().isEmpty()) {
//             bindingResult.rejectValue("email", null, "Email này đã được sử dụng!");
//         }

//         if(bindingResult.hasErrors()) {
//             model.addAttribute("user", user);
//             return "shopper/register";
//         }
//         userService.saveUser(user);
//         return "redirect:/register?success";
//     }
// }
