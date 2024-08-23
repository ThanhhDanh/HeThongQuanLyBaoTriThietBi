/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ltd.controllers;

import com.ltd.pojo.Equipmentlocation;
import com.ltd.pojo.Maintenanceschedule;
import com.ltd.service.EquipmentLocationService;
import com.ltd.service.MaintenanceScheduleService;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 *
 * @author Acer
 */
@Controller
public class ManageEquipmentMaintenanceSchedulesController {

    @Autowired
    private MaintenanceScheduleService maintenanceScheduleService;

    @Autowired
    private EquipmentLocationService equipmentLocationService;

    @GetMapping("/maintenanceschedules")
    public String index(Model model,
            @RequestParam(value = "startDate", required = false) String startDateStr,
            @RequestParam(value = "endDate", required = false) String endDateStr,
            @RequestParam(value = "currentLocation", required = false) String currentLocation,
            @RequestParam(value = "page", required = false) Integer page) {

        if (page == null || page < 1) {
            page = 1;  // Đặt trang mặc định là 1 nếu không có giá trị
        }

        int maxResults = 9; // Số lượng kết quả trên mỗi trang

        List<Maintenanceschedule> maintenanceschedules = this.maintenanceScheduleService.getMaintenanceschedule(page, maxResults);
        List<Equipmentlocation> equipmentlocations = this.equipmentLocationService.getEquipLocation();

        //Phân trang
        // Lấy danh sách thiết bị và số lượng thiết bị
        long maintenanceScheduleCounter = this.maintenanceScheduleService.countAllMaintenanceSchedule();
        model.addAttribute("maintenanceScheduleCounter", maintenanceScheduleCounter);

        // Cập nhật trạng thái cho mỗi lịch bảo trì
        for (Maintenanceschedule schedule : maintenanceschedules) {
            String status = this.maintenanceScheduleService.getMaintenanceStatus(schedule);
            schedule.setMaintenanceStatus(status);
        }

        // Lọc theo khoảng thời gian nếu có
        if (startDateStr != null && !startDateStr.isEmpty() && endDateStr != null && !endDateStr.isEmpty()) {
            LocalDate startDate = LocalDate.parse(startDateStr);
            LocalDate endDate = LocalDate.parse(endDateStr);

            maintenanceschedules = maintenanceschedules.stream()
                    .filter(schedule -> {
                        // Chuyển đổi từ java.sql.Date sang java.util.Date rồi sang LocalDate
                        Date nextMaintenanceDateUtil = new Date(schedule.getNextMaintenanceDate().getTime());
                        LocalDate nextMaintenanceDate = nextMaintenanceDateUtil.toInstant()
                                .atZone(ZoneId.systemDefault())
                                .toLocalDate();
                        return !nextMaintenanceDate.isBefore(startDate) && !nextMaintenanceDate.isAfter(endDate);
                    })
                    .collect(Collectors.toList());
        }

        //Lọc vị trí
        if (currentLocation != null && !currentLocation.equals("0")) {
            Set<Integer> validEquipmentIds = equipmentlocations.stream()
                    .filter(e -> e.getCurrentLocation().equals(currentLocation))
                    .map(e -> e.getEquipmentId().getId()) // Đảm bảo getEquipmentId trả về Integer
                    .collect(Collectors.toSet());

            System.out.println("validEquipmentIds: " + validEquipmentIds);

            maintenanceschedules = maintenanceschedules.stream()
                    .filter(schedule -> validEquipmentIds.contains(schedule.getEquipmentId().getId()))
                    .collect(Collectors.toList());

            System.out.println("maintenanceschedules: " + maintenanceschedules);
        }

        model.addAttribute("maintenanceSchedule", maintenanceschedules);
        model.addAttribute("equipmentlocations", equipmentlocations);
        return "manageEquipmentMaintenanceSchedules";
    }

//    @GetMapping("/dashboard")
//    public String dashboard(Model model) {
//        List<Maintenanceschedule> overdueAndUpcomingSchedules = this.maintenanceScheduleService.getOverdueAndUpcomingSchedules();
//        model.addAttribute("overdueAndUpcomingSchedules", overdueAndUpcomingSchedules);
//        return "dashboard";
//    }
}
