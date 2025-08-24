package com.sonht.e_commerce_webapp_spring_boot.config;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import com.sonht.e_commerce_webapp_spring_boot.dto.ProductToCartDto;

import java.io.IOException;

@Component
public class CustomAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response,
                                        Authentication authentication)
            throws IOException, ServletException {

          // Lấy thông tin user vừa login
          String username = authentication.getName();
          System.out.println("Login success for user: " + username);
  
          // Lấy session hiện tại
          HttpSession session = request.getSession(false);
  
          String redirectUrl = "/home"; // default
          if (session != null) {
              // Kiểm tra xem có productId được lưu trước khi login không
              ProductToCartDto productId = (ProductToCartDto) session.getAttribute("DATA_BEFORE_LOGIN");
              if (productId != null) {
                  // Redirect sang /cart kèm productId
                  redirectUrl = "/user/cart";
              }
          }
  
          response.sendRedirect(redirectUrl);
    }
}

