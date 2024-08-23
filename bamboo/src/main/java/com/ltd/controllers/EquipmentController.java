/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ltd.controllers;

import com.ltd.pojo.Equipment;
import com.ltd.pojo.Equipmentimage;
import com.ltd.pojo.Equipmentlocation;
import com.ltd.pojo.Incident;
import com.ltd.pojo.Maintenanceschedule;
import com.ltd.pojo.Repairhistory;
import com.ltd.service.EquipmentImageService;
import com.ltd.service.EquipmentLocationService;
import com.ltd.service.EquipmentService;
import com.ltd.service.IncidentService;
import com.ltd.service.MaintenanceScheduleService;
import com.ltd.service.RepairHistoryService;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
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
public class EquipmentController {

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

    @GetMapping("/equipment")
    public String equipmentView(Model model) {
        model.addAttribute("equipment", new Equipment());
        model.addAttribute("equipImg", new Equipmentimage());
        model.addAttribute("equipLocation", new Equipmentlocation());
        return "equipment";
    }

    @GetMapping("/equipment/details/{id}")
    public String viewEquipmentDetails(@PathVariable("id") Integer id, Model model,
            @RequestParam(value = "edit", required = false) boolean edit) {
        Equipment equipment = equipmentService.getEquipmentById(id);
        Equipmentimage equipmentImages = equipmentImageService.getEquipmentImgByEquipmentId(id);
        Equipmentlocation equipmentLocation = equipmentLocationService.getEquipmentLocationByEquipmentId(id);
        List<Maintenanceschedule> maintenanceSchedule = maintenanceScheduleService.getMaintenancescheduleByEquipmentId(id);
        Incident incident = incidentService.getIncidentByEquipmentId(id);
        List<Repairhistory> existingRepairHistories = repairHistoryService.getRepairHistoriesByEquipmentId(id);

        model.addAttribute("equipment", equipment);
        model.addAttribute("equipImg", equipmentImages);
        model.addAttribute("equipLocation", equipmentLocation != null ? equipmentLocation : new Equipmentlocation());
        model.addAttribute("mainSchedule", maintenanceSchedule != null ? maintenanceSchedule : new ArrayList<>());
        model.addAttribute("incident", incident != null ? incident : new Incident());
        model.addAttribute("repairHistories", existingRepairHistories != null ? existingRepairHistories : new ArrayList<>());

        // Dành cho trường hợp cần chỉnh sửa
        model.addAttribute("repairHistory", new Repairhistory());
        model.addAttribute("maintenanceSchedules", new Maintenanceschedule());

        model.addAttribute("editMode", edit);   // biến sửa thiết bị

        return "equipmentDetails";
    }

    @PostMapping("/equipment")
    public String create(Model model, @ModelAttribute(value = "equipment") @Valid Equipment e,
            @ModelAttribute(value = "equipImg") @Valid Equipmentimage eimg,
            @ModelAttribute(value = "equipLocation") @Valid Equipmentlocation equipLocation,
            BindingResult rs
    ) {
        System.out.println("Lỗi: " + rs);
        System.out.println("Lỗi: " + rs.hasErrors());
        if (rs.hasErrors()) {
            return "equipment";
        }

        // Thiết lập ngày hiện tại nếu không được cung cấp
        if (e.getPurchaseDate() == null && equipLocation.getCreatedDate() == null && equipLocation.getUpdateDate() == null) {
            e.setPurchaseDate(new Date());
            equipLocation.setCreatedDate(new Date());
            equipLocation.setUpdateDate(new Date());
        }

        // Lưu thiết bị
        this.equipmentService.addOrUpdate(e);

        if (e.getId() != null) {
            equipLocation.setEquipmentId(e);
        }
        this.equipmentLocationService.updateEquipmentLocation(equipLocation);

        // Kiểm tra và xử lý hình ảnh
        if (e.getId() != null) {
            eimg.setEquipmentId(e);
            Equipmentimage existingImage = this.equipmentImageService.getEquipmentImgByEquipmentId(e.getId());

            if (existingImage != null) {
                // Copy ID từ ảnh hiện tại sang đối tượng ảnh mới
                eimg.setId(existingImage.getId());
                if (eimg.getFile() == null || eimg.getFile().isEmpty()) {
                    eimg.setImage(existingImage.getImage());
                } else {
                    // Xử lý upload hình ảnh mới và cập nhật đối tượng Equipmentimage
                    this.equipmentImageService.saveEquipImage(eimg);
                }
            } else {
                // Trường hợp không tìm thấy ảnh cũ (dành cho thiết bị đã tồn tại nhưng chưa có ảnh)
                this.equipmentImageService.saveEquipImage(eimg);
            }
        } else {
            eimg.setEquipmentId(e);
            // Lưu hình ảnh thiết bị mới
            this.equipmentImageService.saveEquipImage(eimg);
        }

        System.out.println("equipLocation: " + equipLocation);

        return "redirect:/";//Ký pháp chuyển về trang chủ
    }

    //Cập nhật thiết bị
    @GetMapping("/equipment/{equipmentId}")
    public String equipmentView(Model model, @PathVariable(value = "equipmentId") int id
    ) {
        model.addAttribute("equipment", this.equipmentService.getEquipmentById(id));
        model.addAttribute("equipImg", this.equipmentImageService.getEquipmentImgByEquipmentId(id));
        model.addAttribute("equipLocation", this.equipmentLocationService.getEquipmentLocationByEquipmentId(id));

        return "equipment";
    }

}
