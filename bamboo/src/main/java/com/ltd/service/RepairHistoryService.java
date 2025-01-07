/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.ltd.service;

import com.ltd.pojo.Repairhistory;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Acer
 */
public interface RepairHistoryService {
    List<Repairhistory> getRepairHistory();
    Repairhistory getRepairHistoryById(int id);
    List<Repairhistory> getRepairHistoriesByEquipmentId(int equipmentId);
    List<Repairhistory> getRepairHistoriesByIncidentId(int incidentId);
    void addOrUpdateRepairHistory(Repairhistory rh);
    Map<Integer, List<Repairhistory>> getRepairHistoryReportByEquipmentGroup(List<Integer> equipmentIds);
}
