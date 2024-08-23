/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ltd.service.impl;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.ltd.pojo.Equipmentimage;
import com.ltd.repository.EquipmentImageRepository;
import com.ltd.service.EquipmentImageService;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;



/**
 *
 * @author Acer
 */
@Service
public class EquipmentImageServiceImpl implements EquipmentImageService {
    
    @Autowired
    private EquipmentImageRepository equipImgRepo;
    
    @Autowired
    private Cloudinary cloudinary;

    @Override
    public List<Equipmentimage> getEquipImage(Map<String, String> params) {
        return this.equipImgRepo.getEquipImage(params);
    }

    @Override
    public void saveEquipImage(Equipmentimage equipmentimage) {
        if (equipmentimage.getFile() != null && !equipmentimage.getFile().isEmpty()) {
            try {
                Map res = this.cloudinary.uploader().upload(equipmentimage.getFile().getBytes(),
                        ObjectUtils.asMap("resource_type", "auto"));
                equipmentimage.setImage(res.get("secure_url").toString());
            } catch (IOException ex) {
                Logger.getLogger(EquipmentServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        this.equipImgRepo.saveEquipImage(equipmentimage);
    }

    @Override
    public Equipmentimage getEquipmentImgById(int id) {
        return this.equipImgRepo.getEquipmentImgById(id);
    }

    @Override
    public Equipmentimage getEquipmentImgByEquipmentId(int equipmentId) {
        return this.equipImgRepo.getEquipmentImgByEquipmentId(equipmentId);
    }
    
}
