/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ltd.controllers;

import com.ltd.pojo.Equipment;
import com.ltd.pojo.Maintenanceschedule;
import com.ltd.service.CategoryService;
import com.ltd.service.EquipmentImageService;
import com.ltd.service.EquipmentService;
import com.ltd.service.MaintenanceScheduleService;
import com.ltd.service.ServiceService;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 *
 * @author Acer
 */
@Controller
@ControllerAdvice
public class HomeController {

    @Autowired
    private EquipmentService equipSer;
    @Autowired
    private ServiceService serSer;

    @Autowired
    private CategoryService cateService;

    @Autowired
    private EquipmentImageService equipImgSer;

    @Autowired
    private MaintenanceScheduleService maintenanceScheduleService;

    @ModelAttribute
    public void commonAttributes(Model model) {
        model.addAttribute("cates", this.cateService.getCates());
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @RequestMapping("/")
    public String index(Model model, @RequestParam Map<String, String> params) {

        if (!params.containsKey("page") || params.get("page") == null || params.get("page").isEmpty()) {
            params.put("page", "1");
        }


        // Lấy danh sách thiết bị và số lượng thiết bị
        long equipmentCounter = this.equipSer.countAllEquipments();
        model.addAttribute("equipmentCounter", equipmentCounter);

        model.addAttribute("equipment", this.equipSer.getEquipment(params));
        model.addAttribute("service", this.serSer.getService(params));
        model.addAttribute("equipImg", this.equipImgSer.getEquipImage(params));

        List<Maintenanceschedule> overdueAndUpcomingSchedules = this.maintenanceScheduleService.getOverdueAndUpcomingSchedules();
        model.addAttribute("schedules", overdueAndUpcomingSchedules);
        
        model.addAttribute("currentPage", Integer.parseInt(params.get("page")));

        return "home";
    }

}
