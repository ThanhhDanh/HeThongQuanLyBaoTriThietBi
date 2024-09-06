/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ltd.repository.impl;

import com.ltd.repository.MailRepository;
import com.ltd.service.MailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Acer
 */
@Repository
@Transactional
public class MailRepositoryImpl implements MailRepository {

    private static final Logger logger = LoggerFactory.getLogger(MailService.class);

    @Autowired
    private MailSender mailSender;

    @Override
    public void sendMail(String to, String subject, String content) {

        SimpleMailMessage mailMessage = new SimpleMailMessage();

        mailMessage.setTo(to);
        mailMessage.setSubject(subject);
        mailMessage.setText(content);

//        mailSender.send(mailMessage);
        try {
            mailSender.send(mailMessage);
            logger.info("Gửi email thành công tới: " + to);
        } catch (Exception e) {
            logger.error("Lỗi khi gửi email thành công tới: " + to, e);
        }
    }
}
