package com.sonht.e_commerce_webapp_spring_boot.controller.customer;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.sonht.e_commerce_webapp_spring_boot.dto.CartItemDto;
import com.sonht.e_commerce_webapp_spring_boot.dto.OrderWebDto;
import com.sonht.e_commerce_webapp_spring_boot.entity.User;
import com.sonht.e_commerce_webapp_spring_boot.service.CartService;
import com.sonht.e_commerce_webapp_spring_boot.service.UserService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;


@Controller
public class CartController {
    private final CartService cartService;
    private final UserService userService;

    public CartController(CartService cartService, UserService userService) {
        this.cartService = cartService;
        this.userService = userService;
    }

    @PostMapping("/saveItem")
    @ResponseBody
    public String saveItemToSession(HttpServletRequest request, @RequestBody CartItemDto cartItemRequest) {
        HttpSession session = request.getSession();

        session.setAttribute("cartItemRequest", cartItemRequest);
        
        return "Thêm sản phẩm thành công"; // Redirect to the cart page
    }

    @GetMapping("/cart")
    public String showCart(Model model, HttpServletRequest request) {
        // Get the current user's from session
        HttpSession session = request.getSession();
        String username = (String) session.getAttribute("username");
        CartItemDto cartItemRequest=  (CartItemDto) session.getAttribute("cartItemRequest");
        // System.out.println(cartItemRequest);
        if(username == null && cartItemRequest == null) {
            return "redirect:/user/login"; // Redirect to login if not authenticated
        }
        if(username != null && userService.findByEmail(username).getCartItems().isEmpty() && cartItemRequest == null) {
            return "shopper/empty-cart"; // Return the cart view
        }
        if (cartItemRequest != null) {

            cartService.handleAddProductToCart(cartItemRequest, username);
            session.removeAttribute("cartItemRequest");
        } 
        // Fetch the user and their cart items
        User user = userService.findByEmail(username);
        model.addAttribute("cartItems", user.getCartItems());
        model.addAttribute("customer", user);

        Long totalPrice = cartService.calculateTotalPrice(user.getCartItems());
        model.addAttribute("totalAmount", totalPrice);
        OrderWebDto orderWebDto = new OrderWebDto();
        orderWebDto.setTotalAmount(totalPrice);
        orderWebDto.setCustomerId(user.getId());
        orderWebDto.setConsignee(user.getName());
        orderWebDto.setConsigneePhone(user.getPhone());
        orderWebDto.setDeliveryAddress(user.getAddress());
        
        model.addAttribute("orderWebDto", orderWebDto);
        
        return "shopper/cart"; // Return the cart view
    }

    @PostMapping("/remove-cart/{cartItemId}")
    @ResponseBody
    public String removeCart(@PathVariable Long cartItemId) {
        cartService.removeCartItem(cartItemId);

        return "Xóa sản phẩm thành công"; 
    }
    @PostMapping("/remove-all")
    @ResponseBody
    public String removeAllCartItems() {
        cartService.removeAllCartItems();

        return "Xóa sản phẩm tất cả sản phẩm thành công";
    }
    

}
