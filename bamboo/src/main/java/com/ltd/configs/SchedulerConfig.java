/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ltd.configs;

import com.ltd.pojo.Maintenanceschedule;
import com.ltd.service.MaintenanceScheduleNotifierService;
import com.ltd.service.MaintenanceScheduleService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

/**
 *
 * @author Acer
 */
@Configuration
@EnableScheduling
public class SchedulerConfig {

    @Autowired
    private MaintenanceScheduleNotifierService maintenanceScheduleNotifierService;

    @Autowired
    private MaintenanceScheduleService maintenanceScheduleService;

    @Scheduled(cron = "0 0 12 * * ?")
    public void scheduleTaskWithCronExpression() {
        this.maintenanceScheduleNotifierService.checkAndSendMaintenanceNotifications();
    }

    @Scheduled(cron = "0 0 12 * * ?")
    public void autoCreateMaintenanceSchedule() {
        // Lấy tất cả các lịch bảo trì đã tồn tại
        List<Maintenanceschedule> schedules = 
                this.maintenanceScheduleService.getMaintenanceschedule(1, Integer.MAX_VALUE);//lấy tất cả các lịch bảo trì

        for (Maintenanceschedule schedule : schedules) {
            int equipmentId = schedule.getEquipmentId().getId();

            // Lấy lịch bảo trì gần nhất cho thiết bị tương ứng
            Maintenanceschedule existingSchedule = this.maintenanceScheduleService
                    .findTopByEquipmentIdOrderByMaintenanceDateDesc(equipmentId);

            // Kiểm tra nếu có lịch bảo trì hiện tại
            if (existingSchedule != null) {
                // Tạo lịch bảo trì mới dựa trên lịch hiện tại
                this.maintenanceScheduleService.createAutomaticMaintenanceSchedule(existingSchedule);
            }
        }
    }

}
