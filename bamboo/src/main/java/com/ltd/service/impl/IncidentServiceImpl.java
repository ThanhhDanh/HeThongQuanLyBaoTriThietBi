/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ltd.service.impl;

import com.ltd.pojo.Incident;
import com.ltd.repository.IncidentRepository;
import com.ltd.service.IncidentService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Acer
 */
@Service
public class IncidentServiceImpl implements IncidentService{
    
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
    
}
