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
import org.springframework.web.bind.annotation.RequestParam;



@Controller
public class CartController {
    private final CartService cartService;
    private final UserService userService;

    public CartController(CartService cartService, UserService userService) {
        this.cartService = cartService;
        this.userService = userService;
    }
    /*
     * Lưu sản phẩm vào session
     */
    @PostMapping("/saveItem")
    @ResponseBody
    public String saveItemToSession(HttpServletRequest request, @RequestBody CartItemDto cartItemRequest) {
        HttpSession session = request.getSession();

        session.setAttribute("cartItemRequest", cartItemRequest);
        
        return "Thêm sản phẩm thành công"; 
    }

    /*
     * Xử lý trang giỏ hàng
     */
    @GetMapping("/user/cart")
    public String showCart(Model model, HttpServletRequest request) {
        // Lấy thông tin người dùng và sản phẩm từ session 
        HttpSession session = request.getSession();
        String username = (String) session.getAttribute("username");
        CartItemDto cartItemRequest=  (CartItemDto) session.getAttribute("cartItemRequest");
        
        // Nếu chưa đăng nhập và không có sản phẩm nào được thêm vào giỏ hàng, chuyển hướng đến trang đăng nhập
        if(username == null && cartItemRequest == null) {
            System.out.println("Chưa đăng nhập");
            return "redirect:/user/login"; 
        }
        // Nếu đã đăng nhập nhưng giỏ hàng trống và không có sản phẩm nào được thêm vào giỏ hàng, 
        // hiển thị trang giỏ hàng trống
        if(username != null && userService.findByEmail(username).getCartItems().isEmpty() && cartItemRequest == null) {
            return "shopper/empty-cart"; 
        }
        // Nếu có sản phẩm được thêm vào giỏ hàng, xử lý thêm sản phẩm vào giỏ hàng của người dùng
        if (cartItemRequest != null) {
            // xử lý thêm sản phẩm vào giỏ hàng của người dùng
            cartService.handleAddProductToCart(cartItemRequest, username);
            session.removeAttribute("cartItemRequest");
        } 
        // Lấy thông tin giỏ hàng của người dùng đã đăng nhập
        User user = userService.findByEmail(username);
        model.addAttribute("cartItems", user.getCartItems());
        model.addAttribute("customer", user);
        // Tính tổng tiền giỏ hàng
        Long totalPrice = cartService.calculateTotalPrice(user.getCartItems());
        model.addAttribute("totalAmount", totalPrice);

        // Tạo đối tượng OrderWebDto để hiển thị thông tin đơn hàng trên trang giỏ hàng
        OrderWebDto orderWebDto = new OrderWebDto();
        orderWebDto.setTotalAmount(totalPrice);
        orderWebDto.setCustomerId(user.getId());
        orderWebDto.setConsignee(user.getName());
        orderWebDto.setConsigneePhone(user.getPhone());
        orderWebDto.setDeliveryAddress(user.getAddress());
        
        model.addAttribute("orderWebDto", orderWebDto);
        
        return "shopper/cart"; // Trả về trang giỏ hàng
    }

    /*
     * Xử lý xóa sản phẩm khỏi giỏ hàng
     */
    @PostMapping("/remove-cart/{cartItemId}")
    @ResponseBody
    public String removeCart(@PathVariable Long cartItemId) {
        // Xóa sản phẩm khỏi giỏ hàng dựa trên cartItemId
        cartService.removeCartItem(cartItemId);

        return "Xóa sản phẩm thành công"; 
    }
    /*
     * Xử lý xóa tất cả sản phẩm khỏi giỏ hàng
     */
    @PostMapping("/remove-all")
    @ResponseBody
    public String removeAllCartItems() {

        // Xóa tất cả sản phẩm khỏi giỏ hàng
        cartService.removeAllCartItems();

        return "Xóa sản phẩm tất cả sản phẩm thành công";
    }
    
    /*
     * Xử lý thay đổi số lượng sản phẩm trong giỏ hàng
      * @param cartItem: id của cartItem cần thay đổi số lượng
      * @param change: hành động thay đổi số lượng ("increase" hoặc "decrease")
      * @return chuyển hướng về trang giỏ hàng sau khi thay đổi số lượng
     */
    @GetMapping("/change-quantity")
    public String getMethodName(@RequestParam("id") String cartItem, @RequestParam("change") String change) {
        cartService.changeQuantity(Long.parseLong(cartItem), change);
        return "redirect:/user/cart";
    }
    

}
