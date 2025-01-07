/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ltd.service.impl;

import com.ltd.pojo.Incident;
import com.ltd.repository.IncidentRepository;
import com.ltd.service.IncidentService;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Acer
 */
@Service
public class IncidentServiceImpl implements IncidentService {

    @Autowired
    private IncidentRepository incidentRepo;

    @Override
    public List<Incident> getIncident() {
        return this.incidentRepo.getIncident();
    }

    @Override
    public Incident getIncidentById(int id) {
        return this.incidentRepo.getIncidentById(id);
    }

    @Override
    public Incident getIncidentByEquipmentId(int equipmentId) {
        return this.incidentRepo.getIncidentByEquipmentId(equipmentId);
    }

    @Override
    public void addOrUpdateIncident(Incident i) {
        this.incidentRepo.addOrUpdateIncident(i);
    }

    @Override
    public long countIncident() {
        return this.incidentRepo.countIncident();
    }

    @Override
    public Map<String, Long> countIncidentsBySeverity() {
        return this.incidentRepo.countIncidentsBySeverity();
    }

    @Override
    public Map<String, Double> calculateSeverityPercentages() {
        Map<String, Long> severityCounts = incidentRepo.countIncidentsBySeverity();
        long totalIncidents = severityCounts.values().stream().mapToLong(Long::longValue).sum();

        Map<String, Double> severityPercentages = new HashMap<>();
        for (Map.Entry<String, Long> entry : severityCounts.entrySet()) {
            double percentage = ((double) entry.getValue() / totalIncidents) * 100;
            severityPercentages.put(entry.getKey(), percentage);
        }

        return severityPercentages;
    }

    @Override
    public void markAsRead(int incidentId) {
        Incident incident = incidentRepo.getIncidentById(incidentId);
        if (incident == null) {
            throw new RuntimeException("Incident not found");
        }
        incident.setIsRead(true);  // Đánh dấu là đã đọc
        incidentRepo.addOrUpdateIncident(incident);  // Lưu thay đổi vào cơ sở dữ liệu
    }

    @Override
    public List<Incident> getUnreadIncidents() {
        return this.incidentRepo.findByIsReadFalse();
    }
}
