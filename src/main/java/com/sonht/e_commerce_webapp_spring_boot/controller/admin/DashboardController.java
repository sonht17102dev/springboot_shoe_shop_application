package com.sonht.e_commerce_webapp_spring_boot.controller.admin;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("admin")
public class DashboardController {

    @GetMapping({"/", "/home" })
    public String dashboard() {

        return "admin/layout";
    }
}
