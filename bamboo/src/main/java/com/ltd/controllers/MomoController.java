/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ltd.controllers;

import com.ltd.pojo.Equipment;
import com.ltd.pojo.Invoice;
import com.ltd.pojo.Invoicedetail;
import com.ltd.pojo.User;
import com.ltd.service.EquipmentService;
import com.ltd.service.InvoiceDetailsService;
import com.ltd.service.InvoiceService;
import com.ltd.service.UserService;
import java.math.BigDecimal;
import java.net.URI;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

/**
 *
 * @author Acer
 */
@RestController
@RequestMapping("/api")
@CrossOrigin
public class MomoController {

    private static final String endpoint = "https://test-payment.momo.vn/v2/gateway/api/create";
    private static final String ipnUrl = "https://c123-2001-ee0-d78a-8890-7c32-16bf-34e5-43c6.ngrok-free.app/bamboo/api/momo_ipn";
    private static final String accessKey = "F8BBA842ECF85";
    private static final String secretKey = "K951B6PE1waDMi640xX08PD3vg6EkVlz";
    private static final String partnerCode = "MOMO";

    @Autowired
    private InvoiceService invoiceService;

    @Autowired
    private EquipmentService equipmentService;

    @Autowired
    private InvoiceDetailsService invoicedetailService;

    @Autowired
    private UserService userService;

    @PostMapping("/process")
    public ResponseEntity<?> processPayment(@RequestBody Map<String, Object> requestData) {
        try {
            String equipmentId = requestData.get("equipmentId").toString();
            String amount = requestData.get("price").toString();
            String userId = requestData.get("userId").toString();
            String quantity = requestData.get("quantity").toString();

            String requestId = UUID.randomUUID().toString();
            String orderId = UUID.randomUUID().toString();
            String requestType = "captureWallet";
            String extraData = "userId=" + userId + ";quantity=" + quantity;
            String redirectUrl = "http://localhost:3000/payment?status=success&message=" + URLEncoder.encode("Thanh toán thành công.", "UTF-8");

            System.out.println("Request Data: " + requestData);
            System.out.println("Equipment ID: " + equipmentId);
            System.out.println("Amount: " + amount);
            System.out.println("User ID: " + userId);
            System.out.println("Quantity: " + quantity);

            String rawSignature = "accessKey=" + accessKey + "&amount=" + amount + "&extraData=" + extraData
                    + "&ipnUrl=" + ipnUrl + "&orderId=" + orderId + "&orderInfo=" + equipmentId
                    + "&partnerCode=" + partnerCode + "&redirectUrl=" + redirectUrl
                    + "&requestId=" + requestId + "&requestType=" + requestType;

            String signature = hmacSHA256(rawSignature, secretKey);

            // Prepare data for request
            Map<String, Object> data = new HashMap<>();
            data.put("partnerCode", partnerCode);
            data.put("partnerName", "Test");
            data.put("storeId", "MomoTestStore");
            data.put("requestId", requestId);
            data.put("amount", amount);
            data.put("orderId", orderId);
            data.put("orderInfo", equipmentId);
            data.put("orderInfo", equipmentId);
            data.put("redirectUrl", redirectUrl);
            data.put("ipnUrl", ipnUrl);
            data.put("lang", "vi");
            data.put("extraData", extraData);
            data.put("requestType", requestType);
            data.put("signature", signature);
            data.put("orderExpireTime", 10);

            System.out.println("Momo API Endpoint: " + endpoint);
            System.out.println("Raw Signature: " + rawSignature);
            System.out.println("Signature: " + signature);

            // Make request to Momo API
            RestTemplate restTemplate = new RestTemplate();
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);

            HttpEntity<Map<String, Object>> entity = new HttpEntity<>(data, headers);
            ResponseEntity<String> response = restTemplate.exchange(endpoint, HttpMethod.POST, entity, String.class);
            System.out.println("Momo API Response: " + response.getBody());
            System.out.println("Momo API Status Code: " + response.getStatusCode());
            return new ResponseEntity<>(response.getBody(), HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>("Error processing payment: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/momo_ipn")
    public ResponseEntity<?> momoIpn(@RequestBody Map<String, Object> ipnData) {
        System.out.println("ipnData: " + ipnData);
        try {
            String orderId = ipnData.get("orderInfo").toString();
            int resultCode = Integer.parseInt(ipnData.get("resultCode").toString());
            System.out.println("resultCode: " + resultCode);

            if (resultCode == 0) {
                String extraData = ipnData.get("extraData").toString();
                String userId = null;
                int quantity = 0; // Khởi tạo số lượng

                // Tách userId và quantity từ extraData
                String[] extraDataParts = extraData.split(";");
                for (String part : extraDataParts) {
                    if (part.startsWith("userId=")) {
                        userId = part.split("=")[1];
                    } else if (part.startsWith("quantity=")) {
                        quantity = Integer.parseInt(part.split("=")[1]);
                    }
                }

                // Lấy amount từ ipnData
                BigDecimal amount = new BigDecimal(ipnData.get("amount").toString());
                System.out.println("Extracted amount: " + amount);

                Date currentDate = new Date();

                // Tạo hóa đơn mới
                Invoice newInvoice = new Invoice();
                newInvoice.setTotalAmount(amount.multiply(BigDecimal.valueOf(quantity)));
                newInvoice.setStatusPayment("DONE");
                newInvoice.setCreatedDate(currentDate);

                if (userId != null) {
                    User user = userService.getUserById(Integer.parseInt(userId));
                    newInvoice.setUserId(user);
                }

                // Lưu hóa đơn và lấy ID
                invoiceService.save(newInvoice);

                // Tạo chi tiết hóa đơn
                Invoicedetail invoicedetail = new Invoicedetail();
                invoicedetail.setQuantity(quantity);
                invoicedetail.setUnitPrice(amount);

                // Lấy Equipment bằng ID từ orderId (ID thiết bị)
                Equipment equipment = equipmentService.getEquipmentById(Integer.parseInt(orderId));
                invoicedetail.setEquipmentId(equipment);
                invoicedetail.setInvoiceId(newInvoice);

                // Lưu chi tiết hóa đơn
                invoicedetailService.save(invoicedetail);

                // Redirect URL chỉ chứa thông tin cần thiết
                String redirectUrl = "http://localhost:3000/payment?status=success&message=" + URLEncoder.encode("Thanh toán thành công.", "UTF-8");
                return ResponseEntity.status(HttpStatus.FOUND)
                        .location(URI.create(redirectUrl))
                        .build();

            } else {
                // Redirect nếu thanh toán không thành công
                String redirectUrl = "http://localhost:3000/payment?status=fail&message=" + URLEncoder.encode("Thanh toán không thành công.", "UTF-8");
                return ResponseEntity.status(HttpStatus.FOUND)
                        .location(URI.create(redirectUrl))
                        .build();
            }
        } catch (Exception e) {
            e.printStackTrace(); // In ra lỗi để dễ dàng kiểm tra
            return new ResponseEntity<>("Invalid data", HttpStatus.BAD_REQUEST);
        }
    }

// Helper method for HMAC SHA256
    private String hmacSHA256(String data, String secret) throws Exception {
        Mac sha256Hmac = Mac.getInstance("HmacSHA256");
        SecretKeySpec secretKeySpec = new SecretKeySpec(secret.getBytes(), "HmacSHA256");
        sha256Hmac.init(secretKeySpec);
        byte[] bytes = sha256Hmac.doFinal(data.getBytes());
        return DatatypeConverter.printHexBinary(bytes).toLowerCase();
    }
}
