package com.sonht.e_commerce_webapp_spring_boot.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import com.sonht.e_commerce_webapp_spring_boot.service.UserService;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {

	private final CustomAuthenticationSuccessHandler successHandler;
	private final CustomLogoutSuccessHandler logoutSuccessHandler;

	public SecurityConfiguration(CustomAuthenticationSuccessHandler successHandler,
			CustomLogoutSuccessHandler logoutSuccessHandler) {
		this.successHandler = successHandler;
		this.logoutSuccessHandler = logoutSuccessHandler;
	}

	// định nghĩa bean passwordEncoder
	@Bean
	public static BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	// định nghĩa bean authenticationProvider
	@Bean
	public static DaoAuthenticationProvider authenticationProvider(UserService userService) {
		DaoAuthenticationProvider auth = new DaoAuthenticationProvider();
		auth.setUserDetailsService(userService); // thiết lập userDetailsService
		auth.setPasswordEncoder(passwordEncoder()); // thiết lập password encoder - bcrypt
		return auth;
	}

	/*
	 * Cấu hình bảo mật cho các URL cho các trang admin
	 */
	@Bean
	@Order(1)
	public SecurityFilterChain filterChainApp1(HttpSecurity http) throws Exception {

		http
				.csrf(csrf -> csrf.disable())
				.securityMatcher("/admin/**")
				.authorizeHttpRequests(configurer -> configurer
						.requestMatchers("/admin/assets/**").permitAll() // cho phép truy cập tài nguyên tĩnh
						// .requestMatchers("/admin/assets/media/logos/**").permitAll()
						.requestMatchers("/admin/**").hasRole("ADMIN") // chỉ cho phép ADMIN truy cập các URL admin
						.anyRequest().authenticated()

				)
				.formLogin(form -> form
						.loginPage("/admin/login") // cấu hình trang login
						.loginProcessingUrl("/admin/login") // cấu hình URL submit form login
						.defaultSuccessUrl("/admin/home") // cấu hình trang sau khi đăng nhập thành công
						.permitAll())
				.logout(logout -> logout
						.logoutUrl("/admin/logout") // cấu hình URL logout
						.logoutSuccessUrl("/admin/login") // cấu hình trang sau khi logout thành công
						.clearAuthentication(true))
				// cấu hình trang khi truy cập bị từ chối
				.exceptionHandling(configurer -> configurer.accessDeniedPage("/error/access-denied")

				);

		return http.build();
	}

	/*
	 * Cấu hình bảo mật cho các URL cho các trang user
	 */
	@Bean
	@Order(2)
	public SecurityFilterChain filterChainApp2(HttpSecurity http) throws Exception {

		http
				.csrf(csrf -> csrf.disable())
				.securityMatcher("/user/**")
				.authorizeHttpRequests(configurer -> configurer
						.requestMatchers("/assets/**").permitAll() // cho phép truy cập tài nguyên tĩnh
						.requestMatchers("/register").permitAll()  // cho phép truy cập trang đăng ký
						// .requestMatchers("/my-account", "/cart").authenticated()
						.requestMatchers("/user/**").hasRole("CUSTOMER") // chỉ cho phép CUSTOMER truy cập các URL user
						.anyRequest().authenticated())
				.formLogin(form -> form
						.loginPage("/user/login") // cấu hình trang login
						.loginProcessingUrl("/user/login") // cấu hình URL submit form login user
						// .defaultSuccessUrl("/user/index")
						.successHandler(successHandler) // sử dụng custom handler

						.permitAll())
				.logout(logout -> logout.logoutUrl("/user/logout") // cấu hình URL logout
						.logoutSuccessHandler(logoutSuccessHandler) // ✅ sử dụng custom logout
						.permitAll()
				)
				// cấu hình trang khi truy cập bị từ chối
				.exceptionHandling(configurer -> configurer.accessDeniedPage("/error/access-denied"));

		return http.build();
	}
}
