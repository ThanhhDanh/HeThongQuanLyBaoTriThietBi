/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.ltd.service;

import com.ltd.pojo.Equipment;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Acer
 */
public interface EquipmentService {
    List<Equipment> getEquipment(Map<String, String> params);
    long countAllEquipments();
    void addOrUpdate(Equipment p);
    Equipment getEquipmentById(int id);
    void deleteEquipment(int id);
}
