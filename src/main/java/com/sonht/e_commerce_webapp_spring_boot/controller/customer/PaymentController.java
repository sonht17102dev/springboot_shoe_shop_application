package com.sonht.e_commerce_webapp_spring_boot.controller.customer;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.sonht.e_commerce_webapp_spring_boot.util.VNPAYUtil;

import jakarta.servlet.http.HttpServletRequest;

import java.text.SimpleDateFormat;
import java.util.*;

@Controller
public class PaymentController {

    @Value("${vnpay.tmnCode}")
    private String vnp_TmnCode;

    @Value("${vnpay.hashSecret}")
    private String vnp_HashSecret;

    @Value("${vnpay.payUrl}")
    private String vnp_Url;

    @Value("${vnpay.returnUrl}")
    private String vnp_Returnurl;

    @GetMapping("/payment/create")
    public String createPayment(HttpServletRequest request, @RequestParam("amount") long amount) throws Exception {
        String vnp_TxnRef = VNPAYUtil.generateRandomNumber(8); // mã giao dịch duy nhất
        String vnp_IpAddr = request.getRemoteAddr();

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

        String query = VNPAYUtil.buildQuery(vnp_Params);
        String vnp_SecureHash = VNPAYUtil.hmacSHA512(vnp_HashSecret, query);
        String paymentUrl = vnp_Url + "?" + query + "&vnp_SecureHash=" + vnp_SecureHash;

        return "redirect:" + paymentUrl;
    }

    @GetMapping("/payment/vnpay-return")
    public String vnpayReturn(HttpServletRequest request, Map<String,Object> model) {
        Map<String, String[]> params = request.getParameterMap();
        Map<String, String> fields = new HashMap<>();
        for (String key : params.keySet()) {
            if (!key.equals("vnp_SecureHash")) {
                fields.put(key, params.get(key)[0]);
            }
        }
        String query = "";
        try {
            query = VNPAYUtil.buildQuery(fields);
        } catch (Exception e) {
            e.printStackTrace();
        }
        String secureHash = VNPAYUtil.hmacSHA512(vnp_HashSecret, query);

        if (secureHash.equals(request.getParameter("vnp_SecureHash"))) {
            String vnp_ResponseCode = request.getParameter("vnp_ResponseCode");
            if ("00".equals(vnp_ResponseCode)) {
                model.put("message", "Thanh toán thành công!");
            } else {
                model.put("message", "Thanh toán thất bại, mã: " + vnp_ResponseCode);
            }
        } else {
            model.put("message", "Chữ ký không hợp lệ!");
        }

        return "shopper/vnpay-return"; 
    }
}

