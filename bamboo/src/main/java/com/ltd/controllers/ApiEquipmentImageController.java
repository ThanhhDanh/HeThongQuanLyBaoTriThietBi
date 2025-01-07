/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ltd.controllers;

import com.ltd.pojo.Equipmentimage;
import com.ltd.service.EquipmentImageService;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author Acer
 */
@RestController
@RequestMapping("/api")
@CrossOrigin
public class ApiEquipmentImageController {
    @Autowired
    private EquipmentImageService equipImgService;

    @GetMapping("/equipmentimages")
    public ResponseEntity<List<Equipmentimage>> list(@RequestParam Map<String, String> params ) {
        return new ResponseEntity<>(this.equipImgService.getEquipImage(params), HttpStatus.OK);
    }
}
