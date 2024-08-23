/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ltd.service.impl;

import com.ltd.pojo.Maintenanceschedule;
import com.ltd.repository.MaintenanceScheduleNotifierRepository;
import com.ltd.service.MaintenanceScheduleNotifierService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Acer
 */
@Service
public class MaintenanceScheduleNotifierServiceImpl implements MaintenanceScheduleNotifierService {

    @Autowired
    private MaintenanceScheduleNotifierRepository notify;

    @Override
    public void checkAndSendMaintenanceNotifications() {
        this.notify.checkAndSendMaintenanceNotifications();
    }

    @Override
    public void sendEmailToEmployees(List<Maintenanceschedule> schedules) {
        this.notify.sendEmailToEmployees(schedules);
    }

    @Override
    public void sendEmailToTechnicians(List<Maintenanceschedule> schedules) {
        this.notify.sendEmailToTechnicians(schedules);
    }

}
