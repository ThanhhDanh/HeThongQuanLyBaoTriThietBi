/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.ltd.repository;

import com.ltd.pojo.Maintenanceschedule;
import java.util.List;

/**
 *
 * @author Acer
 */
public interface MaintenanceScheduleNotifierRepository {
    void checkAndSendMaintenanceNotifications();
    void sendEmailToEmployees(List<Maintenanceschedule> schedules);
    void sendEmailToTechnicians(List<Maintenanceschedule> schedules);
}
