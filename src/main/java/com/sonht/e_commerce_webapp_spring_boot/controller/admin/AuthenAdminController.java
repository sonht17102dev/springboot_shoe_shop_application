package com.sonht.e_commerce_webapp_spring_boot.controller.admin;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AuthenAdminController {


    @GetMapping("/admin/login")
    public String userLogin() {

        return "admin/login";
    }

    @GetMapping("/error/access-denied")
    public String logoutAdmin() {
        return  "/error/access-denied";
    }

}
