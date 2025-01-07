/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ltd.controllers;

import com.ltd.service.IncidentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 *
 * @author Acer
 */
@Controller
public class NotificationController {

    @Autowired
    private IncidentService incidentService;

    @PostMapping("/mark-as-read/{incidentId}")
    @ResponseBody // Để trả về JSON mà không redirect
    public ResponseEntity<String> markAsRead(@PathVariable int incidentId) {
        try {
            incidentService.markAsRead(incidentId);
            return ResponseEntity.ok("Thông báo đã được đánh dấu là đã đọc.");
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Đánh dấu không thành công: " + e.getMessage());
        }
    }
}
