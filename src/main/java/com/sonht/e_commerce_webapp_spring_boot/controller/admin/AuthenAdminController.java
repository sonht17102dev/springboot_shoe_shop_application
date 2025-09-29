package com.sonht.e_commerce_webapp_spring_boot.controller.admin;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AuthenAdminController {

    /*
     * Xử lý trang đăng nhập admin
     */
    @GetMapping("/admin/login")
    public String userLogin() {

        return "admin/login";
    }

    /*
     * Xử lý trang không có quyền truy cập
     */
    @GetMapping("/error/access-denied")
    public String logoutAdmin() {
        return  "/error/access-denied";
    }

}
