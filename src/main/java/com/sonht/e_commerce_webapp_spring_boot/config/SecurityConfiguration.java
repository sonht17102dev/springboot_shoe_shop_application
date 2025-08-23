package com.sonht.e_commerce_webapp_spring_boot.config;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import com.sonht.e_commerce_webapp_spring_boot.service.UserService;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {
    
    private final UserDetailsService userDetailsService;
    private final CustomAuthenticationSuccessHandler successHandler;

    public SecurityConfiguration(UserDetailsService userDetailsService, CustomAuthenticationSuccessHandler successHandler) {
        this.userDetailsService = userDetailsService;
        this.successHandler = successHandler;
    }

    //bcrypt bean definition
    @Bean
    public static BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    //authenticationProvider bean definition
    @Bean
    public static DaoAuthenticationProvider authenticationProvider(UserService userService) {
        DaoAuthenticationProvider auth = new DaoAuthenticationProvider();
        auth.setUserDetailsService(userService); //set the custom user details service
        auth.setPasswordEncoder(passwordEncoder()); //set the password encoder - bcrypt
        return auth;
    }

    @Bean
    @Order(1)
    public SecurityFilterChain filterChainApp1(HttpSecurity http) throws Exception {


        http
                .csrf(csrf -> csrf.disable())
                .securityMatcher("/admin/**")
                .authorizeHttpRequests(configurer ->
                        configurer
                        .requestMatchers("/admin/assets/**").permitAll()
                        // .requestMatchers("/admin/assets/media/logos/**").permitAll()
                        .requestMatchers("/admin/**").hasRole("ADMIN")
                                .anyRequest().authenticated()

                )
                .formLogin(form ->
                        form
                                .loginPage("/admin/login")
                                .loginProcessingUrl("/admin/login")
                                .defaultSuccessUrl("/admin/home")
                                .permitAll()
                )
                .logout(logout ->
                        logout
                                .logoutUrl("/admin/logout")
                        .logoutSuccessUrl("/admin/login")
                        .clearAuthentication(true)
                )
                .exceptionHandling(configurer ->
                        configurer.accessDeniedPage("/error/access-denied")

                );

        return http.build();
    }

    @Bean
    @Order(2)
    public SecurityFilterChain filterChainApp2(HttpSecurity http) throws Exception {


        http
                .csrf(csrf -> csrf.disable())
                .securityMatcher("/user/**")
                .authorizeHttpRequests(configurer ->
                        configurer
                                .requestMatchers("/assets/**").permitAll()
                                .requestMatchers("/register").permitAll()
                                .requestMatchers("/add-to-cart/**").authenticated()
                                .requestMatchers("/user/**").hasRole("CUSTOMER")
                                .anyRequest().authenticated()
                )
                .formLogin(form ->
                        form
                                .loginPage("/user/login")
                                .loginProcessingUrl("/user/login")
                                // .defaultSuccessUrl("/user/my-account")
                                .successHandler(successHandler)   // sử dụng custom handler

                                .permitAll()
                )
                .logout(logout -> logout.logoutUrl("/user/logout").permitAll().logoutSuccessUrl("/index")
                )
                .exceptionHandling(configurer ->
                        configurer.accessDeniedPage("/error/access-denied")
                );

        return http.build();
    }
}
