package com.sonht.e_commerce_webapp_spring_boot.util;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.*;

public class VNPAYUtil {

    /*
     * Hàm băm HMAC SHA512
     */
    public static String hmacSHA512(String key, String data) {
        try {
            javax.crypto.Mac hmac512 = javax.crypto.Mac.getInstance("HmacSHA512");
            javax.crypto.spec.SecretKeySpec secretKey = new javax.crypto.spec.SecretKeySpec(key.getBytes(), "HmacSHA512");
            hmac512.init(secretKey);
            byte[] bytes = hmac512.doFinal(data.getBytes("UTF-8"));
            StringBuilder hash = new StringBuilder();
            for (byte b : bytes) {
                hash.append(String.format("%02x", b));
            }
            return hash.toString();
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    /*
     * Hàm xây dựng chuỗi truy vấn từ map các tham số
     */
    public static String buildQuery(Map<String, String> params) throws UnsupportedEncodingException {
        List<String> fieldNames = new ArrayList<>(params.keySet());
        Collections.sort(fieldNames);
        StringBuilder sb = new StringBuilder();
        for (String fieldName : fieldNames) {
            String fieldValue = params.get(fieldName);
            if (fieldValue != null && fieldValue.length() > 0) {
                sb.append(URLEncoder.encode(fieldName, "UTF-8"));
                sb.append('=');
                sb.append(URLEncoder.encode(fieldValue, "UTF-8"));
                sb.append('&');
            }
        }
        // remove last '&'
        sb.deleteCharAt(sb.length() - 1);
        return sb.toString();
    }

   
}

