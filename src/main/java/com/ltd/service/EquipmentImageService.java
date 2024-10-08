/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.ltd.service;

import com.ltd.pojo.Equipmentimage;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Acer
 */
public interface EquipmentImageService {
    List<Equipmentimage> getEquipImage(Map<String, String> params);
    void saveEquipImage(Equipmentimage equipmentimage);
    Equipmentimage getEquipmentImgById(int id);
    Equipmentimage getEquipmentImgByEquipmentId(int equipmentId);
}
