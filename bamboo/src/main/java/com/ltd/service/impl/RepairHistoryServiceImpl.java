/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ltd.service.impl;

import com.ltd.pojo.Repairhistory;
import com.ltd.repository.RepairHistoryRepository;
import com.ltd.service.RepairHistoryService;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Acer
 */
@Service
public class RepairHistoryServiceImpl implements RepairHistoryService {

    @Autowired
    private RepairHistoryRepository repairHistoryRepo;

    @Override
    public List<Repairhistory> getRepairHistory() {
        return this.repairHistoryRepo.getRepairHistory();
    }

    @Override
    public Repairhistory getRepairHistoryById(int id) {
        return this.repairHistoryRepo.getRepairHistoryById(id);
    }

    @Override
    public List<Repairhistory> getRepairHistoriesByEquipmentId(int equipmentId) {
        return this.repairHistoryRepo.getRepairHistoriesByEquipmentId(equipmentId);
    }

    @Override
    public void addOrUpdateRepairHistory(Repairhistory rh) {
        this.repairHistoryRepo.addOrUpdateRepairHistory(rh);
    }

    @Override
    public List<Repairhistory> getRepairHistoriesByIncidentId(int incidentId) {
        return this.repairHistoryRepo.getRepairHistoriesByIncidentId(incidentId);
    }

    @Override
    public Map<Integer, List<Repairhistory>> getRepairHistoryReportByEquipmentGroup(List<Integer> equipmentIds) {
        Map<Integer, List<Repairhistory>> report = new HashMap<>();
        for (int id : equipmentIds) {
            report.put(id, repairHistoryRepo.getRepairHistoriesByEquipmentId(id));
        }
        return report;
    }

}
