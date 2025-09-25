package com.sonht.e_commerce_webapp_spring_boot.controller.customer;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.sonht.e_commerce_webapp_spring_boot.util.Ultilities;
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
    public String createPayment(HttpServletRequest request, @RequestParam("amount") long amount, @RequestParam("orderId") String orderId) throws Exception {
        String vnp_TxnRef = Ultilities.getFormatId(orderId); // mã giao dịch duy nhất
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
        } else {
            resultMessage = "Giao dịch Không thành công";
        }
        model.addAttribute("message", resultMessage);

        return "shopper/vnpay-return";
    }

}
