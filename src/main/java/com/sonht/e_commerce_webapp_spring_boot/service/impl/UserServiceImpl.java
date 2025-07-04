package com.sonht.e_commerce_webapp_spring_boot.service.impl;

import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.sonht.e_commerce_webapp_spring_boot.dto.RegistrationDto;
import com.sonht.e_commerce_webapp_spring_boot.entity.Role;
import com.sonht.e_commerce_webapp_spring_boot.entity.User;
import com.sonht.e_commerce_webapp_spring_boot.repository.RoleRepository;
import com.sonht.e_commerce_webapp_spring_boot.repository.UserRepository;
import com.sonht.e_commerce_webapp_spring_boot.service.UserService;

// @Service
// public class UserServiceImpl implements UserService{
//     private UserRepository userRepository;
//     private RoleRepository roleRepository;
//     private PasswordEncoder passwordEncoder;
//     public UserServiceImpl(UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder) {
//         this.userRepository = userRepository;
//         this.roleRepository = roleRepository;
//         this.passwordEncoder = passwordEncoder;
//     }

//     @Override
//     public User findByEmail(String email) {
//         return userRepository.findByEmail(email);
//     }

//     @Override
//     public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
//         User user = userRepository.findByEmail(userName);
//         if (user == null) {

//             throw new UsernameNotFoundException("Invalid username or password.");
//         }
//         System.out.println(user.getEmail());
//         return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(),
//                 mapRolesToAuthorities(user.getRoles()));
//     }

//     private Collection<? extends GrantedAuthority> mapRolesToAuthorities(Collection<Role> roles) {
//         return roles.stream().map(role -> new SimpleGrantedAuthority(role.getName())).collect(Collectors.toList());
//     }

//     @Override
//     public void saveUser(RegistrationDto registrationDto) {
//         User user = new User();
//         user.setName(registrationDto.getName());
//         user.setEmail(registrationDto.getEmail());
//         // use spring security to encrypt the password
//         user.setPassword(passwordEncoder.encode(registrationDto.getPassword()));
//         Role role = roleRepository.findByName("ROLE_CUSTOMER");
//         user.setRoles(Arrays.asList(role));
//         user.setPhone(registrationDto.getPhone());
//         user.setEnabled(true); // enable user by default
//         user.setCreatedAt(Date.from(java.time.Instant.now()));

//         userRepository.save(user);
    
//     }
// }
