/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ltd.service.impl;

import com.ltd.repository.ServiceRepository;
import com.ltd.service.ServiceService;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Acer
 */
@Service
public class ServiceServiceImpl implements ServiceService{
    
    @Autowired
    private ServiceRepository serRepo;

    @Override
    public List<com.ltd.pojo.Service> getService(Map<String, String> params) {
        return this.serRepo.getService(params);
    }
    
}
