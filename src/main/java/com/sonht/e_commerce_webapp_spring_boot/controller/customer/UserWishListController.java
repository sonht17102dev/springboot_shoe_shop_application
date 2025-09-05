package com.sonht.e_commerce_webapp_spring_boot.controller.customer;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sonht.e_commerce_webapp_spring_boot.entity.User;
import com.sonht.e_commerce_webapp_spring_boot.service.UserService;
import com.sonht.e_commerce_webapp_spring_boot.service.UserWishListService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
public class UserWishListController {

    private final UserService userService;
    private final UserWishListService userWishlistService;
    

    public UserWishListController(UserService userService, UserWishListService userWishlistService) {
        this.userService = userService;
        this.userWishlistService = userWishlistService;
    }


    @PostMapping("/product-wishlist/{productId}")
    @ResponseBody
    public String handleLike(@PathVariable Long productId, HttpServletRequest request) {
        // Giả sử userId được lấy từ phiên đăng nhập
        HttpSession session = request.getSession();
        String username = (String) session.getAttribute("username");
        User user = userService.findByEmail(username);
        if (user == null) {
            throw new RuntimeException("User not found");
        }
        boolean isLiked = userWishlistService.toggleWishlist(user.getId(), productId); // Giả sử userId là 1

        return isLiked ? "added" : "removed";
    }

    @GetMapping("/user/my-account/wishlist")
    public String getWishListPage(Model model) {
        var userWishlists = userWishlistService.findAllWishlistProducts();
        var products = userWishlists.stream().map(wishlist -> wishlist.getProduct()).toList();
        model.addAttribute("products", products);

        return "shopper/wishlist";
    }
    
}
