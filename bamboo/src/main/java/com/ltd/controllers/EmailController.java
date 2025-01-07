/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ltd.controllers;

import com.ltd.service.MailService;
import java.util.Map;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.beans.factory.annotation.Autowired;

/**
 *
 * @author Acer
 */
@RestController
@RequestMapping("/api")
public class EmailController {

    @Autowired
    private MailService mailService;

    @PostMapping("/send-email")
    @CrossOrigin
    public String sendEmail(@RequestBody Map<String, String> payload) {
        String recipientEmail = payload.get("email");
        String subject = payload.get("subject");
        String messageContent = payload.get("message");

        try {
            mailService.sendEmail(recipientEmail, subject, messageContent);
            return "Email sent successfully!";
        } catch (Exception e) {
            e.printStackTrace();
            return "Failed to send email: " + e.getMessage();
        }
    }
}