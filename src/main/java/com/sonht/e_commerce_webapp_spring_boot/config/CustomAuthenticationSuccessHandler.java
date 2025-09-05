package com.sonht.e_commerce_webapp_spring_boot.config;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import com.sonht.e_commerce_webapp_spring_boot.dto.CartItemDto;
import com.sonht.e_commerce_webapp_spring_boot.entity.User;
import com.sonht.e_commerce_webapp_spring_boot.service.UserService;

import java.io.IOException;

@Component
public class CustomAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    private final UserService userService;

    public CustomAuthenticationSuccessHandler(UserService userService) {
        this.userService = userService;
    }
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
            HttpServletResponse response,
            Authentication authentication)
            throws IOException, ServletException {

        // Lấy thông tin user vừa login
        String username = authentication.getName();
        System.out.println("Login success for user: " + username);

        // Lấy session hiện tại
        HttpSession session = request.getSession();
        session.setAttribute("username", username);
        User user = userService.findByEmail(username);
        session.setAttribute("user", user);

        String redirectUrl = "/index"; // default
        if (session != null) {
            // Kiểm tra xem có productId được lưu trước khi login không
            CartItemDto cartItemRequest = (CartItemDto) session.getAttribute("cartItemRequest");
            if (cartItemRequest != null) {

                // Redirect sang /cart kèm productId
                redirectUrl = "/cart";
            }
        }

        response.sendRedirect(redirectUrl);
    }
}
