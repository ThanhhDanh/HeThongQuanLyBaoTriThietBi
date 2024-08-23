/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ltd.repository.impl;

import com.ltd.pojo.Maintenanceschedule;
import com.ltd.repository.MaintenanceScheduleNotifierRepository;
import java.time.LocalDate;
import java.util.List;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Acer
 */
@Repository
@Transactional
public class MaintenanceScheduleNotifierRepositoryImpl implements MaintenanceScheduleNotifierRepository {

    @Autowired
    private LocalSessionFactoryBean factory;

    @Autowired
    private JavaMailSender mailSender;

    @Override
    public void checkAndSendMaintenanceNotifications() {
        Session session = this.factory.getObject().getCurrentSession();
        LocalDate today = LocalDate.now();

        // Truy vấn các lịch bảo trì đã quá hạn
        List<Maintenanceschedule> overdueSchedules = session.createQuery(
                "FROM Maintenanceschedule ms WHERE ms.nextMaintenanceDate < :today AND ms.maintenanceStatus != 'Đã hoàn thành'")
                .setParameter("today", today)
                .getResultList();

        // Gửi email tới nhân viên và kỹ thuật viên
        sendEmailToEmployees(overdueSchedules);
        sendEmailToTechnicians(overdueSchedules);

    }

    @Override
    public void sendEmailToEmployees(List<Maintenanceschedule> schedules) {
        for (Maintenanceschedule schedule : schedules) {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setTo(schedule.getAssignedUserId().getEmail());  // Email của nhân viên
            message.setSubject("Lịch bảo trì chưa xử lý");
            message.setText("Lịch bảo trì cho thiết bị " + schedule.getEquipmentId().getName() + " chưa được xử lý.");

            mailSender.send(message);
        }
    }

    @Override
    public void sendEmailToTechnicians(List<Maintenanceschedule> schedules) {
        for (Maintenanceschedule schedule : schedules) {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setTo(schedule.getPerformedByUserId().getEmail());  // Email của kỹ thuật viên
            message.setSubject("Lịch bảo trì được phân công");
            message.setText("Bạn đã được phân công bảo trì thiết bị: " + schedule.getEquipmentId().getName());

            mailSender.send(message);
        }
    }

}
