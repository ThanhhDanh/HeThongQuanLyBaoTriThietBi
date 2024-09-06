/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ltd.controllers;

import com.ltd.pojo.Equipment;
import com.ltd.pojo.Equipmentimage;
import com.ltd.pojo.Equipmentlocation;
import com.ltd.pojo.Incident;
import com.ltd.pojo.Invoicedetail;
import com.ltd.pojo.Maintenanceschedule;
import com.ltd.pojo.Repairhistory;
import com.ltd.pojo.User;
import com.ltd.service.EquipmentImageService;
import com.ltd.service.EquipmentLocationService;
import com.ltd.service.EquipmentService;
import com.ltd.service.IncidentService;
import com.ltd.service.InvoiceDetailsService;
import com.ltd.service.MaintenanceScheduleService;
import com.ltd.service.RepairHistoryService;
import com.ltd.service.UserService;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

/**
 *
 * @author Acer
 */
@Controller
public class RepairTechnicalInformationController {

    @Autowired
    private EquipmentService equipmentService;
    
    @Autowired
    private EquipmentImageService equipmentImageService;

    @Autowired
    private EquipmentLocationService equipmentLocationService;

    @Autowired
    private MaintenanceScheduleService maintenanceScheduleService;

    @Autowired
    private IncidentService incidentService;

    @Autowired
    private RepairHistoryService repairHistoryService;

    @Autowired
    private UserService userService;

    @Autowired
    private InvoiceDetailsService invoiceDetailService;

    @GetMapping("/equipment/repairhistory/{equipmentId}")
    public String viewRepairHistoryDetails(@PathVariable("equipmentId") Integer id, Model model) {
        Equipment equipment = equipmentService.getEquipmentById(id);
        Equipmentimage equipmentImages = equipmentImageService.getEquipmentImgByEquipmentId(id);
        Incident incident = incidentService.getIncidentByEquipmentId(id);
        List<Repairhistory> existingRepairHistories = this.repairHistoryService.getRepairHistoriesByEquipmentId(id);

        List<User> technicians = userService.getUsersByRole("ROLE_TECHNICIAN");  // Lấy tất cả kỹ thuật viên

        model.addAttribute("equipment", equipment);
        model.addAttribute("equipImg", equipmentImages);
        model.addAttribute("incident", incident);
        model.addAttribute("repairHistories", existingRepairHistories != null ? existingRepairHistories : new ArrayList<>());
        model.addAttribute("technicians", technicians);

        model.addAttribute("message", "Thiết bị đã được cập nhật thành công!");

        // Dành cho trường hợp cần chỉnh sửa
        model.addAttribute("repairHistory", new Repairhistory());

        return "repairHistory";
    }

    @PostMapping("/equipment/repairhistory/{equipmentId}")
    public String repairHistory(@PathVariable("equipmentId") int equipmentId,
            @ModelAttribute("repairHistory") @Valid Repairhistory repairHistory, BindingResult rs, Model model) {

        Equipment equipment = this.equipmentService.getEquipmentById(equipmentId);
        Incident incident = this.incidentService.getIncidentByEquipmentId(equipmentId);


        // Lấy thông tin người dùng đăng nhập
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName(); // Lấy tên người dùng
        User loggedInUser = userService.getUserByUsername(username);

        repairHistory.setAssignedUserId(loggedInUser);

        repairHistory.setEquipmentId(equipment);
        repairHistory.setIncidentId(incident);

        System.out.println("rs: " + rs);

        if (repairHistory.getRepairDate() == null) {
            repairHistory.setRepairDate(new Date());
        }

        if (rs.hasErrors()) {
            return "repairHistory";
        }


        // Lấy danh sách tất cả các bản sửa chữa cho thiết bị
        List<Repairhistory> existingRepairHistories = this.repairHistoryService.getRepairHistoriesByEquipmentId(equipmentId);

        // Xử lý logic thêm hoặc cập nhật bản sửa chữa
        if (repairHistory.getId() == null) {
            // Thêm bản sửa chữa mới
            this.repairHistoryService.addOrUpdateRepairHistory(repairHistory);
        } else {
            for (Repairhistory existingRepairHistory : existingRepairHistories) {
                if (existingRepairHistory.getId().equals(repairHistory.getId())) {
                    this.repairHistoryService.addOrUpdateRepairHistory(repairHistory);
                    break;
                }
            }
        }

        model.addAttribute("message", "Thiết bị đã được cập nhật thành công!");

        return "redirect:/equipment/details/" + repairHistory.getEquipmentId().getId();
    }

    @GetMapping("/equipment/incident/{equipmentId}")
    public String viewIncidentDetails(@PathVariable("equipmentId") Integer id, Model model) {
        Equipment equipment = equipmentService.getEquipmentById(id);
        Equipmentimage equipmentImages = equipmentImageService.getEquipmentImgByEquipmentId(id);
        Incident incident = incidentService.getIncidentByEquipmentId(id);

        // Lấy danh sách người dùng
        List<User> users = userService.getAllUsers();  // Lấy tất cả người dùng
        List<User> technicians = userService.getUsersByRole("ROLE_TECHNICIAN");  // Lấy tất cả kỹ thuật viên

        model.addAttribute("equipment", equipment);
        model.addAttribute("equipImg", equipmentImages);
        model.addAttribute("incident", incident != null ? incident : new Incident());
        model.addAttribute("users", users);
        model.addAttribute("technicians", technicians);

        model.addAttribute("message", "Thiết bị đã được cập nhật thành công!");

        return "incident";
    }

    @PostMapping("/equipment/incident/{equipmentId}")
    public String incident(@PathVariable("equipmentId") int equipmentId,
            @ModelAttribute("incident") @Valid Incident incident, BindingResult rs, Model model) {

        Equipment equipment = this.equipmentService.getEquipmentById(equipmentId);

        // Lấy thông tin người dùng đăng nhập
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName(); // Lấy tên người dùng
        User loggedInUser = userService.getUserByUsername(username);

        incident.setAssignedUserId(loggedInUser);

        incident.setEquipmentId(equipment);

        System.out.println("rs: " + rs);

        if (incident.getIncidentTime() == null) {
            incident.setIncidentTime(new Date());
        }

        if (rs.hasErrors()) {
            return "incident";
        }

        if (incident.getId() != null) {
            this.incidentService.addOrUpdateIncident(incident);
        } else {
            this.incidentService.addOrUpdateIncident(incident);
        }

        model.addAttribute("message", "Thiết bị đã được cập nhật thành công!");

        return "redirect:/equipment/details/" + incident.getEquipmentId().getId();
    }

    @GetMapping("/equipment/maintenanceschedule/{equipmentId}")
    public String viewMaintenanceSchedule(@PathVariable("equipmentId") Integer id, Model model) {
        Equipment equipment = equipmentService.getEquipmentById(id);
        Equipmentimage equipmentImages = equipmentImageService.getEquipmentImgByEquipmentId(id);
        List<Maintenanceschedule> maintenanceSchedule = maintenanceScheduleService.getMaintenancescheduleByEquipmentId(id);

        List<User> technicians = userService.getUsersByRole("ROLE_TECHNICIAN");  // Lấy tất cả kỹ thuật viên

        model.addAttribute("equipment", equipment);
        model.addAttribute("equipImg", equipmentImages);
        model.addAttribute("maintenanceSchedule", maintenanceSchedule != null ? maintenanceSchedule : new ArrayList<>());

        
        model.addAttribute("maintenanceSchedules", new Maintenanceschedule());
        
        model.addAttribute("technicians", technicians);

        return "maintenanceSchedule";
    }

    //Chuyển đổi về date
    @InitBinder
    public void initBinder(WebDataBinder binder) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        dateFormat.setLenient(false);
        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
    }

    @PostMapping("/equipment/maintenanceschedule/{equipmentId}")
    public String maintenanceSchedule(@PathVariable("equipmentId") int equipmentId,
            @ModelAttribute("maintenanceSchedule") @Valid Maintenanceschedule maintenanceSchedule, BindingResult rs, Model model) {

        Equipment equipment = this.equipmentService.getEquipmentById(equipmentId);

        // Lấy thông tin người dùng đăng nhập
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName(); // Lấy tên người dùng
        User loggedInUser = userService.getUserByUsername(username);

        maintenanceSchedule.setAssignedUserId(loggedInUser);
        maintenanceSchedule.setEquipmentId(equipment);

        // Kiểm tra và gán giá trị cho MaintenanceDate nếu chưa có
        if (maintenanceSchedule.getMaintenanceDate() == null) {
            maintenanceSchedule.setMaintenanceDate(maintenanceSchedule.getNextMaintenanceDate());
        }

        // Gán giá trị ReminderDate là một tuần trước NextMaintenanceDate
        if (maintenanceSchedule.getMaintenanceDate() != null && maintenanceSchedule.getReminderDate() == null) {
            LocalDate nextMaintenanceDate = maintenanceSchedule.getNextMaintenanceDate().toInstant()
                    .atZone(ZoneId.systemDefault()).toLocalDate();
            LocalDate reminderDate = nextMaintenanceDate.minusWeeks(1);
            maintenanceSchedule.setReminderDate(Date.from(reminderDate.atStartOfDay(ZoneId.systemDefault()).toInstant()));
        }

        System.out.println("rs: " + rs);

        if (rs.hasErrors()) {
            return "maintenanceSchedule";
        }

        if (maintenanceSchedule.getId() != null) {
            this.maintenanceScheduleService.addOrUpdateMaintenanceScheduleService(maintenanceSchedule);
        } else {
            this.maintenanceScheduleService.addOrUpdateMaintenanceScheduleService(maintenanceSchedule);
        }

        model.addAttribute("message", "Thiết bị đã được cập nhật thành công!");

        return "redirect:/equipment/details/" + maintenanceSchedule.getEquipmentId().getId();
    }

    @GetMapping("/equipment/devicehistories/{equipmentId}")
    public String deviceHistories(Model model, @PathVariable("equipmentId") int equipmentId) {
        Equipment equipment = equipmentService.getEquipmentById(equipmentId);
        Equipmentimage equipmentImages = equipmentImageService.getEquipmentImgByEquipmentId(equipmentId);

        List<Invoicedetail> invoicedetails = this.invoiceDetailService.getInvoiceDetailByEquipmentId(equipmentId);

        BigDecimal totalInvoiceDetails = invoicedetails.stream()
                .map(Invoicedetail::getUnitPrice)
                .filter(Objects::nonNull)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        List<Repairhistory> existingRepairHistories = this.repairHistoryService.getRepairHistoriesByEquipmentId(equipmentId);
        List<User> users = userService.getAllUsers();
        BigDecimal totalRepairCost = existingRepairHistories.stream()
                .map(Repairhistory::getRepairCost)
                .filter(Objects::nonNull)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        model.addAttribute("equipment", equipment);
        model.addAttribute("equipImg", equipmentImages);
        model.addAttribute("invoiceDetails", invoicedetails != null ? invoicedetails : new ArrayList<>());
        model.addAttribute("repairHistories", existingRepairHistories != null ? existingRepairHistories : new ArrayList<>());

        // Dành cho trường hợp cần chỉnh sửa
        model.addAttribute("repairHistory", new Repairhistory());
        model.addAttribute("totalRepairCost", totalRepairCost);
        model.addAttribute("totalInvoiceDetails", totalInvoiceDetails);
        model.addAttribute("user", users);

        return "deviceHistories";
    }
}
