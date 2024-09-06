/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ltd.controllers;

import com.ltd.pojo.Equipmentlocation;
import com.ltd.pojo.Maintenanceschedule;
import com.ltd.pojo.User;
import com.ltd.service.EquipmentLocationService;
import com.ltd.service.MaintenanceScheduleService;
import com.ltd.service.UserService;
import java.time.LocalDate;
import java.time.ZoneId;
import java.sql.Date;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
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

    @Autowired
    private UserService userService;

    @GetMapping("/maintenanceschedules")
    public String index(Model model,
            @RequestParam(value = "startDate", required = false) String startDateStr,
            @RequestParam(value = "endDate", required = false) String endDateStr,
            @RequestParam(value = "currentLocation", required = false) String currentLocation,
            @RequestParam(value = "page", required = false) Integer page) {
        

        if (page == null || page < 1) {
            page = 1;
        }

        int maxResults = 9;

        List<Maintenanceschedule> maintenanceschedules = this.maintenanceScheduleService.getMaintenanceschedule(page, maxResults);
        List<Equipmentlocation> equipmentlocations = this.equipmentLocationService.getEquipLocation();
        List<User> users = this.userService.getAllUsers();
        
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName(); // Lấy tên người dùng
        User user = this.userService.getUserByUsername(username);
        boolean isTechnician = user.getRole().equals("ROLE_TECHNICIAN");

        //Phân trang
        // Lấy danh sách thiết bị và số lượng thiết bị
        long maintenanceScheduleCounter = this.maintenanceScheduleService.countAllMaintenanceSchedule();
        model.addAttribute("maintenanceScheduleCounter", maintenanceScheduleCounter);
        model.addAttribute("currentPage", page);
        model.addAttribute("isTechnician", isTechnician);

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
        model.addAttribute("users", users);
        return "manageEquipmentMaintenanceSchedules";
    }

    //Cập nhật trạng thái khi hoàn thành
    @PostMapping("/maintenanceschedules/{id}")
    public String updateMaintenanceSchedulesItem(Model model,
            @PathVariable("id") Integer id,
            @ModelAttribute("maintenanceSchedule") @Valid Maintenanceschedule maintenanceSchedule,
            BindingResult rs) {

        System.out.println("rs: " + rs);

        if (rs.hasErrors()) {
            return "manageEquipmentMaintenanceSchedules";
        }

        // Lấy thông tin người dùng đăng nhập
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName(); // Lấy tên người dùng
        User loggedInUser = this.userService.getUserByUsername(username);

        maintenanceSchedule.setPerformedByUserId(loggedInUser);
        maintenanceSchedule.setEquipmentId(maintenanceSchedule.getEquipmentId());

        // Lấy ngày hiện tại
        LocalDate today = LocalDate.now();

        // Chuyển đổi LocalDate thành java.sql.Date
        Date sqlToday = Date.valueOf(today);
        
        maintenanceSchedule.setMaintenanceDate(sqlToday);

        // Gán giá trị ReminderDate là một tuần trước NextMaintenanceDate
        if (maintenanceSchedule.getMaintenanceDate() != null && maintenanceSchedule.getNextMaintenanceDate() != null) {
            LocalDate nextMaintenanceDate = maintenanceSchedule.getNextMaintenanceDate().toInstant()
                    .atZone(ZoneId.systemDefault()).toLocalDate();

            String frequency = maintenanceSchedule.getMaintenanceFrequency();
            switch (frequency) {
                case "Hàng tuần":
                    nextMaintenanceDate = nextMaintenanceDate.plusWeeks(1);
                    break;
                case "Hàng tháng":
                    nextMaintenanceDate = nextMaintenanceDate.plusMonths(1);
                    break;
                case "Hàng năm":
                    nextMaintenanceDate = nextMaintenanceDate.plusYears(1);
                    break;
                default:
                    System.out.println("Tần suất bảo trì không hợp lệ.");
                    break;
            }

            maintenanceSchedule.setNextMaintenanceDate(Date.from(nextMaintenanceDate.atStartOfDay(ZoneId.systemDefault()).toInstant()));

            LocalDate reminderDate = nextMaintenanceDate.minusWeeks(1);
            maintenanceSchedule.setReminderDate(Date.from(reminderDate.atStartOfDay(ZoneId.systemDefault()).toInstant()));

            System.out.println("Ngày tiếp theo được cập nhật: " + maintenanceSchedule.getNextMaintenanceDate());
            System.out.println("Ngày nhắc nhở được cập nhật: " + maintenanceSchedule.getReminderDate());

        } else {
            System.out.println("Cả hai ngày là null.");
        }

        maintenanceSchedule.setMaintenanceStatus("Hoàn thành");
        this.maintenanceScheduleService.addOrUpdateMaintenanceScheduleService(maintenanceSchedule);

        return "redirect:/maintenanceschedules";
    }

}
