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
public interface MaintenanceScheduleRepository {

    List<Maintenanceschedule> getMaintenanceschedule(int page, int maxResults);
    
    long countAllMaintenanceSchedule();

    String getMaintenanceStatus(Maintenanceschedule maintenanceSchedule);

    Maintenanceschedule getMaintenancescheduleById(int id);

    List<Maintenanceschedule> getMaintenancescheduleByEquipmentId(int equipmentId);

    void addOrUpdateMaintenanceScheduleService(Maintenanceschedule maintenanceSchedule);

    List<Maintenanceschedule> getOverdueAndUpcomingSchedules();

    Maintenanceschedule findTopByEquipmentIdOrderByMaintenanceDateDesc(int equipmentId);
}
