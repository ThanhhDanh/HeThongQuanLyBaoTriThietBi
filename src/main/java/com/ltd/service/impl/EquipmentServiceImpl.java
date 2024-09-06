/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ltd.service.impl;


import com.ltd.pojo.Equipment;
import com.ltd.repository.EquipmentRepository;
import com.ltd.service.EquipmentService;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Acer
 */
@Service
public class EquipmentServiceImpl implements EquipmentService {

    @Autowired
    private EquipmentRepository equipRepo;

    


    @Override
    public List<Equipment> getEquipment(Map<String, String> params) {
        return this.equipRepo.getEquipment(params);
    }

    @Override
    public void addOrUpdate(Equipment e) {

        this.equipRepo.addOrUpdate(e);
    }

    @Override
    public Equipment getEquipmentById(int id) {
        return this.equipRepo.getEquipmentById(id);
    }

    @Override
    public void deleteEquipment(int id) {
        //Cần kiểm tra các mối quan hệ trước khi xóa
        this.equipRepo.deleteEquipment(id);
    }

    @Override
    public long countAllEquipments() {
        return this.equipRepo.countAllEquipments();
    }

}
