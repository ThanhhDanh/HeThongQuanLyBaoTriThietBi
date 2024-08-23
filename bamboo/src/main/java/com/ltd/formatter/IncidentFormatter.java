/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ltd.formatter;

import com.ltd.pojo.Incident;
import java.text.ParseException;
import java.util.Locale;
import org.springframework.format.Formatter;

/**
 *
 * @author Acer
 */
public class IncidentFormatter implements Formatter<Incident> {

    @Override
    public String print(Incident incident, Locale locale) {
        return String.valueOf(incident.getId());
    }

    @Override
    public Incident parse(String incidentId, Locale locale) throws ParseException {
        Incident incident = new Incident();
        incident.setId(Integer.parseInt(incidentId));
        return incident;
    }   
}
