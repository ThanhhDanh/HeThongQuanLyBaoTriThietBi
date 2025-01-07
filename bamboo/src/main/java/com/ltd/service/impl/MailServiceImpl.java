/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ltd.service.impl;

import com.ltd.repository.MailRepository;
import com.ltd.service.MailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

/**
 *
 * @author Acer
 */
@Service
public class MailServiceImpl implements MailService{

    @Autowired
    private MailRepository mailRepo;
    
    @Autowired
    private JavaMailSender mailSender;

    @Override
    public void sendMail(String to, String subject, String content) {
        this.mailRepo.sendMail(to, subject, content);
    }

    @Override
    public void sendEmail(String toEmail, String subject, String message) {
        SimpleMailMessage email = new SimpleMailMessage();
        email.setTo(toEmail);
        email.setSubject(subject);
        email.setText(message);
        email.setFrom("97.lethanhdanh.toky@gmail.com");

        // Gửi email
        mailSender.send(email);
    }
    
}
