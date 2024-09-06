/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ltd.service.impl;

import com.ltd.pojo.Maintenanceschedule;
import com.ltd.repository.MaintenanceScheduleRepository;
import com.ltd.service.MaintenanceScheduleService;
import java.util.Calendar;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Acer
 */
@Service
public class MaintenanceScheduleServiceImpl implements MaintenanceScheduleService {

    @Autowired
    private MaintenanceScheduleRepository mainScheduleRepo;

    @Override
    public List<Maintenanceschedule> getMaintenanceschedule(int page, int maxResults) {
        return this.mainScheduleRepo.getMaintenanceschedule(page, maxResults);
    }

    @Override
    public Maintenanceschedule getMaintenancescheduleById(int id) {
        return this.mainScheduleRepo.getMaintenancescheduleById(id);
    }

    @Override
    public List<Maintenanceschedule> getMaintenancescheduleByEquipmentId(int equipmentId) {
        return this.mainScheduleRepo.getMaintenancescheduleByEquipmentId(equipmentId);
    }

    @Override
    public void addOrUpdateMaintenanceScheduleService(Maintenanceschedule maintenanceSchedule) {
        this.mainScheduleRepo.addOrUpdateMaintenanceScheduleService(maintenanceSchedule);
    }

    @Override
    public String getMaintenanceStatus(Maintenanceschedule maintenanceSchedule) {
        return this.mainScheduleRepo.getMaintenanceStatus(maintenanceSchedule);
    }

    @Override
    public List<Maintenanceschedule> getOverdueAndUpcomingSchedules() {
        return this.mainScheduleRepo.getOverdueAndUpcomingSchedules();
    }

    @Override
    public void createAutomaticMaintenanceSchedule(Maintenanceschedule existingSchedule) {
        if (existingSchedule != null
                && "Đang xử lý".equalsIgnoreCase(existingSchedule.getMaintenanceStatus())
                && existingSchedule.getMaintenanceDate() != null) {

            // Tạo lịch bảo trì mới dựa trên lịch bảo trì hiện tại
            Maintenanceschedule newSchedule = new Maintenanceschedule();
            newSchedule.setMaintenanceFrequency(existingSchedule.getMaintenanceFrequency());
            newSchedule.setMaintenanceType(existingSchedule.getMaintenanceType());
            newSchedule.setEquipmentId(existingSchedule.getEquipmentId());
            newSchedule.setAssignedUserId(existingSchedule.getAssignedUserId());
            newSchedule.setPerformedByUserId(existingSchedule.getPerformedByUserId());

            // Tính toán ngày bảo trì tiếp theo và ngày nhắc nhở
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(existingSchedule.getMaintenanceDate());

            switch (existingSchedule.getMaintenanceFrequency().toLowerCase()) {
                case "Hàng tuần":
                    calendar.add(Calendar.WEEK_OF_YEAR, 1);
                    break;
                case "Hàng tháng":
                    calendar.add(Calendar.MONTH, 1);
                    break;
                case "Hàng năm":
                    calendar.add(Calendar.YEAR, 1);
                    break;
                default:
                    calendar.add(Calendar.MONTH, 1);
                    break;
            }

            newSchedule.setMaintenanceDate(existingSchedule.getNextMaintenanceDate());
            newSchedule.setNextMaintenanceDate(calendar.getTime());

            // Tạo ngày nhắc nhở 7 ngày trước ngày bảo trì tiếp theo
            calendar.add(Calendar.DAY_OF_MONTH, -7);
            newSchedule.setReminderDate(calendar.getTime());

            // Cập nhật trạng thái lịch bảo trì mới
            newSchedule.setMaintenanceStatus("Đang xử lý");

            // Lưu lịch bảo trì mới
            mainScheduleRepo.addOrUpdateMaintenanceScheduleService(newSchedule);
        }
    }

    @Override
    public Maintenanceschedule findTopByEquipmentIdOrderByMaintenanceDateDesc(int equipmentId) {
        return this.mainScheduleRepo.findTopByEquipmentIdOrderByMaintenanceDateDesc(equipmentId);
    }

    @Override
    public long countAllMaintenanceSchedule() {
        return this.mainScheduleRepo.countAllMaintenanceSchedule();
    }

}
