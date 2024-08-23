/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ltd.controllers;

import com.ltd.pojo.Equipment;
import com.ltd.pojo.Repairhistory;
import com.ltd.service.EquipmentService;
import com.ltd.service.RepairHistoryService;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 *
 * @author Acer
 */
@Controller
public class RepairHistoryReportController {

     @Autowired
    private EquipmentService equipmentService;

    @Autowired
    private RepairHistoryService repairHistoryService;

    @GetMapping("/repairhistoryreport")
    public String getRepairHistory(Model model, @RequestParam(value = "q", required = false) String query) {
        List<Equipment> equipmentList = equipmentService.getEquipment(null);
        List<Repairhistory> repairHistoryList = repairHistoryService.getRepairHistory();

        if (query != null && !query.isEmpty()) {
            equipmentList = equipmentList.stream()
                    .filter(e -> e.getName().toLowerCase().contains(query.toLowerCase()))
                    .collect(Collectors.toList());
        }

        model.addAttribute("equipmentList", equipmentList);
        model.addAttribute("repairHistory", repairHistoryList);
        return "repairhistoryreport";
    }
}
