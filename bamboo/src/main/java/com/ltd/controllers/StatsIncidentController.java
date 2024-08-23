/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ltd.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ltd.pojo.Incident;
import com.ltd.pojo.Repairhistory;
import com.ltd.service.IncidentService;
import com.ltd.service.RepairHistoryService;
import java.util.List;
import java.util.Map;
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
public class StatsIncidentController {

    @Autowired
    private IncidentService incidentService;

    @Autowired
    private RepairHistoryService repairHistoryService;

    @GetMapping("/incidents")
    public String getIncidents(Model model, @RequestParam(value = "q", required = false) String query) {
        List<Incident> incidents = incidentService.getIncident();
        List<Repairhistory> repairHistoryList = this.repairHistoryService.getRepairHistory();

        if (query != null && !query.isEmpty()) {
            // Lọc sự cố theo tên thiết bị
            incidents = incidents.stream()
                    .filter(i -> i.getEquipmentId().getName().toLowerCase().contains(query.toLowerCase()))
                    .collect(Collectors.toList());
        }

        model.addAttribute("incidents", incidents);
        model.addAttribute("repairHistory", repairHistoryList);

        // Thống kê số liệu cho biểu đồ
        long processed = incidents.stream().filter(i -> i.getIncidentStatus().equals("Đã xử lý")).count();
        long unprocessed = incidents.stream().filter(i -> i.getIncidentStatus().equals("Chưa xử lý")).count();
        long processing = incidents.stream().filter(i -> i.getIncidentStatus().equals("Đang xử lý")).count();

        long[] incidentStatusData = new long[]{processed, unprocessed, processing};

        // Chuyển đổi mảng sang JSON
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            String incidentStatusDataJson = objectMapper.writeValueAsString(incidentStatusData);
            model.addAttribute("incidentStatusData", incidentStatusDataJson);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return "incidentStats";
    }
}
