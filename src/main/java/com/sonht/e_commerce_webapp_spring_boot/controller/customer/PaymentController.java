package com.sonht.e_commerce_webapp_spring_boot.controller.customer;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.sonht.e_commerce_webapp_spring_boot.dto.OrderWebDto;
import com.sonht.e_commerce_webapp_spring_boot.service.OrderService;
import com.sonht.e_commerce_webapp_spring_boot.service.UserService;
import com.sonht.e_commerce_webapp_spring_boot.util.Ultilities;
import com.sonht.e_commerce_webapp_spring_boot.util.VNPAYUtil;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

import java.text.SimpleDateFormat;
import java.util.*;

@Controller
public class PaymentController {

    private final UserService userService;
    private final OrderService orderService;

    public PaymentController(UserService userService, OrderService orderService) {
        this.userService = userService;
        this.orderService = orderService;
    }
    // Cấu hình thông tin VNPAY từ application.properties
    @Value("${vnpay.tmnCode}")
    private String vnp_TmnCode;

    @Value("${vnpay.hashSecret}")
    private String vnp_HashSecret;

    @Value("${vnpay.payUrl}")
    private String vnp_Url;

    @Value("${vnpay.returnUrl}")
    private String vnp_Returnurl;

    /*
     * Xử lý tạo thanh toán và chuyển hướng đến trang thanh toán của VNPAY
     * 
     */
    @GetMapping("/payment/create")
    public String createPayment(HttpServletRequest request) throws Exception {
        HttpSession session = request.getSession();
        OrderWebDto orderWebDto = (OrderWebDto) session.getAttribute("orderWebDto");
        if (orderWebDto == null) {
            return "redirect:/user/cart"; // Chuyển hướng về trang giỏ hàng nếu không có đơn hàng nào trong session
        }
        Long amount = orderWebDto.getTotalAmount(); // Số tiền cần thanh toán

        // Chuyển đổi dữ liệu và lưu đơn hàng, lấy ra orderId
        Long orderId = Ultilities.mappingDataDtoToEntity(orderWebDto, userService, orderService, true).getId();
        String vnp_TxnRef = Ultilities.getFormatId(String.valueOf(orderId)); // Xử lý định dạng mã đơn hàng
        String vnp_IpAddr = request.getRemoteAddr();

        // Tạo các tham số cho URL thanh toán
        Map<String, String> vnp_Params = new HashMap<>();
        vnp_Params.put("vnp_Version", "2.1.0");
        vnp_Params.put("vnp_Command", "pay");
        vnp_Params.put("vnp_TmnCode", vnp_TmnCode);
        vnp_Params.put("vnp_Amount", String.valueOf(amount * 100)); // nhân 100

        vnp_Params.put("vnp_CurrCode", "VND");
        vnp_Params.put("vnp_TxnRef", vnp_TxnRef);
        vnp_Params.put("vnp_OrderInfo", "Thanh toan don hang:" + vnp_TxnRef);
        vnp_Params.put("vnp_OrderType", "other");
        vnp_Params.put("vnp_Locale", "vn");
        vnp_Params.put("vnp_ReturnUrl", vnp_Returnurl);
        vnp_Params.put("vnp_IpAddr", vnp_IpAddr);

        Calendar cld = Calendar.getInstance(TimeZone.getTimeZone("Etc/GMT+7"));
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
        String vnp_CreateDate = formatter.format(cld.getTime());
        vnp_Params.put("vnp_CreateDate", vnp_CreateDate);

        cld.add(Calendar.MINUTE, 15);
        String vnp_ExpireDate = formatter.format(cld.getTime());
        vnp_Params.put("vnp_ExpireDate", vnp_ExpireDate);

        // Sắp xếp tham số theo key
        String query = VNPAYUtil.buildQuery(vnp_Params);
        // tạo chữ ký
        String vnp_SecureHash = VNPAYUtil.hmacSHA512(vnp_HashSecret, query);
        
        String paymentUrl = vnp_Url + "?" + query + "&vnp_SecureHash=" + vnp_SecureHash;
        session.removeAttribute("orderWebDto"); // Xóa đơn hàng trong session sau khi tạo URL thanh toán
        return "redirect:" + paymentUrl;
    }

    /*
     * Xử lý trang kết quả thanh toán từ VNPAY trả về
     */
    @GetMapping("/vnpay-return")
    public String paymentReturn(
            @RequestParam(name = "vnp_TxnRef", required = false) String txnRef,
            @RequestParam(name = "vnp_Amount", required = false) String amount,
            @RequestParam(name = "vnp_OrderInfo", required = false) String orderInfo,
            @RequestParam(name = "vnp_ResponseCode", required = false) String responseCode,
            @RequestParam(name = "vnp_TransactionNo", required = false) String transactionNo,
            @RequestParam(name = "vnp_BankCode", required = false) String bankCode,
            @RequestParam(name = "vnp_PayDate", required = false) String payDate,
            Model model) {
        model.addAttribute("txnRef", txnRef);
        model.addAttribute("amount", amount);
        model.addAttribute("orderInfo", orderInfo);
        model.addAttribute("responseCode", responseCode);
        model.addAttribute("transactionNo", transactionNo);
        model.addAttribute("bankCode", bankCode);
        model.addAttribute("payDate", payDate);

        // logic kiểm tra kết quả
        String resultMessage;
        if ("00".equals(responseCode)) {
            resultMessage = "Giao dịch thành công";
            model.addAttribute("message", resultMessage);
            return "shopper/vnpay-return";
        } else {
            return "redirect:/user/cart";
        }
    }

}
