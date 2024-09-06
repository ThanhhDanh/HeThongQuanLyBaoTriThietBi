/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ltd.service.impl;

import com.ltd.pojo.Equipmentlocation;
import com.ltd.repository.EquipmentLocationRepository;
import com.ltd.service.EquipmentLocationService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Acer
 */
@Service
public class EquipmentLocationServiceImpl implements EquipmentLocationService{
    
    @Autowired
    private EquipmentLocationRepository equipLocationRepo;

    @Override
    public List<Equipmentlocation> getEquipLocation() {
        return this.equipLocationRepo.getEquipLocation();
    }

    @Override
    public Equipmentlocation getEquipmentLocationById(int id) {
        return this.equipLocationRepo.getEquipmentLocationById(id);
    }

    @Override
    public Equipmentlocation getEquipmentLocationByEquipmentId(int equipmentId) {
        return this.equipLocationRepo.getEquipmentLocationByEquipmentId(equipmentId);
    }

    @Override
    public void updateEquipmentLocation(Equipmentlocation equipLocation) {
        this.equipLocationRepo.updateEquipmentLocation(equipLocation);
    }
    
}
