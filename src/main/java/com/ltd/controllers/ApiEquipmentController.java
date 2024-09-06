/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ltd.controllers;

import com.ltd.pojo.Equipment;
import com.ltd.service.EquipmentService;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author Acer
 */
@RestController
@RequestMapping("/api")
@CrossOrigin
public class ApiEquipmentController {

    @Autowired
    private EquipmentService equipSer;
    
    @GetMapping("/equipments")
    public ResponseEntity<Map<String, Object>> list(@RequestParam Map<String, String> params ) {
        List<Equipment> equipment = this.equipSer.getEquipment(params);
        
        long totalCount = this.equipSer.countAllEquipments();

         Map<String, Object> response = new HashMap<>();
        response.put("data", equipment);
        response.put("totalCount", totalCount);
        
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    

    @DeleteMapping("/equipment/{equipmentId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void destroy(@PathVariable(value = "equipmentId") int id) {
        this.equipSer.deleteEquipment(id);
    }
}
