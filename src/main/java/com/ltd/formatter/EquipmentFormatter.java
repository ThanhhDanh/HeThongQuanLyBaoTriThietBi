/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ltd.formatter;

import com.ltd.pojo.Equipment;
import java.text.ParseException;
import java.util.Locale;
import org.springframework.format.Formatter;


/**
 *
 * @author Acer
 */
public class EquipmentFormatter implements Formatter<Equipment> {

    @Override
    public String print(Equipment equipment, Locale locale) {
        return String.valueOf(equipment.getId());
    }

    @Override
    public Equipment parse(String equipmentId, Locale locale) throws ParseException {
        Equipment e = new Equipment();
        e.setId(Integer.parseInt(equipmentId));
        
        return e;
    }
}
