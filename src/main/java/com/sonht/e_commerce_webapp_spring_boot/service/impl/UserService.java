package com.sonht.e_commerce_webapp_spring_boot.service.impl;

import com.sonht.e_commerce_webapp_spring_boot.entity.User;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {

    User findByEmail(String email);
}
