/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.ltd.service;

import com.ltd.pojo.Maintenanceschedule;
import java.util.List;

/**
 *
 * @author Acer
 */
public interface MaintenanceScheduleNotifierService {
    void checkAndSendMaintenanceNotifications();
    void sendEmailToEmployees(List<Maintenanceschedule> schedules);
    void sendEmailToTechnicians(List<Maintenanceschedule> schedules);
}
