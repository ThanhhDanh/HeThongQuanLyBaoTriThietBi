/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.ltd.service;

import com.ltd.pojo.Incident;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Acer
 */
public interface IncidentService {

    List<Incident> getIncident();

    Incident getIncidentById(int id);

    Incident getIncidentByEquipmentId(int equipmentId);

    void addOrUpdateIncident(Incident i);

    long countIncident();
    
    Map<String, Long> countIncidentsBySeverity();
    
    Map<String, Double> calculateSeverityPercentages();
    
    void markAsRead(int incidentId);
    
    List<Incident> getUnreadIncidents();
}
