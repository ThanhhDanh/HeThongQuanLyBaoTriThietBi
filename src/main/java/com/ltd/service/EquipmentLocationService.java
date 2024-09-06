/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.ltd.service;

import com.ltd.pojo.Equipmentlocation;
import java.util.List;

/**
 *
 * @author Acer
 */
public interface EquipmentLocationService {
    List<Equipmentlocation> getEquipLocation();
    Equipmentlocation getEquipmentLocationById(int id);
    Equipmentlocation getEquipmentLocationByEquipmentId(int equipmentId);
    void updateEquipmentLocation(Equipmentlocation equipLocation);
}
