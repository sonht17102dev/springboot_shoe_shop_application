package com.sonht.e_commerce_webapp_spring_boot.config;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.security.web.savedrequest.SavedRequest;
import org.springframework.stereotype.Component;

import com.sonht.e_commerce_webapp_spring_boot.entity.User;
import com.sonht.e_commerce_webapp_spring_boot.service.UserService;

import java.io.IOException;

@Component
public class CustomAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    private final UserService userService;

    private final RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();

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
        // Lấy thông tin user từ database và lưu vào session
        User user = userService.findByEmail(username);
        session.setAttribute("user", user);


        // Lấy URL mà Spring Security đã lưu
        SavedRequest savedRequest = new HttpSessionRequestCache().getRequest(request, response);
        // Nếu có URL lưu thì chuyển hướng đến URL đó
        if (savedRequest != null) {
            String targetUrl = savedRequest.getRedirectUrl();
            
            redirectStrategy.sendRedirect(request, response, targetUrl);

        } else {
            // Nếu không có thì về trang chủ
            redirectStrategy.sendRedirect(request, response, "/");

        }
    }
}
