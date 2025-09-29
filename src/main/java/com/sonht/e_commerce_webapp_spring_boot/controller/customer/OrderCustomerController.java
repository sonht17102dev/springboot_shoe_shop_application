package com.sonht.e_commerce_webapp_spring_boot.controller.customer;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.sonht.e_commerce_webapp_spring_boot.dto.OrderWebDto;
import com.sonht.e_commerce_webapp_spring_boot.entity.OrderWeb;
import com.sonht.e_commerce_webapp_spring_boot.service.OrderService;
import com.sonht.e_commerce_webapp_spring_boot.service.UserService;
import com.sonht.e_commerce_webapp_spring_boot.util.Ultilities;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.ResponseBody;


@Controller
public class OrderCustomerController {

    private UserService userService;
    private OrderService orderService;

    public OrderCustomerController(UserService userService, OrderService orderService) {
        this.userService = userService;
        this.orderService = orderService;
    }
    /*
     * Xử lý đặt hàng
     */

    @PostMapping("/checkout")
    public String handleOrder(@Valid @ModelAttribute OrderWebDto orderWebDto, BindingResult result, Model model, HttpServletRequest re) {
        HttpSession session = re.getSession();
        // validate form
        if (result.hasErrors()) {
            model.addAttribute("orderWebDto", orderWebDto);
            return "shopper/cart";
        }
        // Xử lý thanh toán qua ATM
        if(orderWebDto.getPaymentMethod().equals("ATM") ) {
            session.setAttribute("orderWebDto", orderWebDto);
            return "redirect:/payment/create";
        }
        // Chuyển đổi dữ liệu và lưu đơn hàng
        OrderWeb orderWeb = Ultilities.mappingDataDtoToEntity(orderWebDto, userService, orderService, false);

        // hiện thị thông tin lên order-result
        model.addAttribute("orderWeb", orderWeb);
        model.addAttribute("result", "Đặt hàng thành công");
        model.addAttribute("note", "Cảm ơn bạn đã mua hàng tại TopShoe");
        model.addAttribute("isSuccess", true);

        return "shopper/order-result";
    }

    /*
     * Xử lý hủy đơn hàng
     */
    @PostMapping("/cancel-order/{orderWebId}")
    @ResponseBody
    public String handleConfirmOrder(@PathVariable Long orderWebId) {
        // hủy đơn hàng theo orderWebId
        orderService.cancelOrder(orderWebId);
        return "cancelled";
    }

    
}
