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
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Acer
 */
@RestController
@RequestMapping("/api")
@CrossOrigin
public class ApiInvoiceController {

    @Autowired
    private InvoiceService invoiceService;

    @Autowired
    private EquipmentService equipmentService;

    @Autowired
    private InvoiceDetailsService invoicedetailService;

    @Autowired
    private UserService userService;

    @Autowired
    private InvoiceDetailsService invoiceDetailService;

    @GetMapping("/invoice/{userId}")
    public ResponseEntity<List<Invoice>> getInvoicesByUserId(@PathVariable Integer userId) {
        List<Invoice> invoices = invoiceService.findInvoicesByUserId(userId);
        return new ResponseEntity<>(invoices, HttpStatus.OK);
    }

    @PostMapping("/invoice")
    public ResponseEntity<?> handleCashPayment(@RequestBody Map<String, Object> requestData) {
        System.out.println("requestData: " + requestData);
        try {
            if (requestData != null) {
                Map<String, Object> data = (Map<String, Object>) requestData.get("data");
                // Lấy thông tin cần thiết từ request body
                String userId = data.get("userId").toString();
                String equipmentId = data.get("equipmentId").toString();
                Integer quantity = ((Number) data.get("quantity")).intValue();

                User user = userService.getUserById(Integer.parseInt(userId));
                BigDecimal price = new BigDecimal(data.get("price").toString());
                Date currentDate = new Date();

                // Tạo đối tượng hóa đơn
                Invoice invoice = new Invoice();
                invoice.setUserId(user);
                invoice.setTotalAmount(price.multiply(BigDecimal.valueOf(quantity)));
                invoice.setCreatedDate(currentDate);
                invoice.setStatusPayment("PENDING");

                // Lưu hóa đơn vào cơ sở dữ liệu và trả về đối tượng hóa đơn đã lưu
                invoiceService.save(invoice);

                // Tạo đối tượng chi tiết hóa đơn
                Equipment equipment = equipmentService.getEquipmentById(Integer.parseInt(equipmentId));
                Invoicedetail invoiceDetail = new Invoicedetail();
                invoiceDetail.setInvoiceId(invoice); // Gán hóa đơn đã lưu thay vì invoice
                invoiceDetail.setEquipmentId(equipment);
                invoiceDetail.setUnitPrice(price);
                invoiceDetail.setQuantity(quantity);

                // Lưu chi tiết hóa đơn vào cơ sở dữ liệu
                invoicedetailService.save(invoiceDetail);

                // Trả về phản hồi thành công
                return new ResponseEntity<>(invoiceDetail, HttpStatus.CREATED);
            } else {
                return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
            }
        } catch (Exception e) {
            e.printStackTrace(); // Giúp debug dễ hơn nếu có lỗi xảy ra
            return new ResponseEntity<>(Collections.singletonMap("Lỗi", "Đã xảy ra lỗi khi tạo hóa đơn."), HttpStatus.BAD_REQUEST);
        }
    }
}
