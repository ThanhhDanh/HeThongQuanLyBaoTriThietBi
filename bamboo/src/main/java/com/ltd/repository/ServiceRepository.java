/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.ltd.repository;

import com.ltd.pojo.Service;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Acer
 */
public interface ServiceRepository {
    List<Service> getService(Map<String, String> params);   
}
