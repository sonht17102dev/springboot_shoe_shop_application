package com.sonht.e_commerce_webapp_spring_boot.service;

import com.sonht.e_commerce_webapp_spring_boot.dto.RegistrationDto;
import com.sonht.e_commerce_webapp_spring_boot.entity.User;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {
    void saveUser(RegistrationDto registrationDto);
    User findByEmail(String email);
}
