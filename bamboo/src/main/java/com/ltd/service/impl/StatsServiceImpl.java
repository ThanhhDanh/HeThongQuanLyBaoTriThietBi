/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ltd.service.impl;

import com.ltd.repository.StatsRepository;
import com.ltd.service.StatsService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Acer
 */
@Service
public class StatsServiceImpl implements StatsService {
    
    @Autowired
    private StatsRepository statsRepo;

    @Override
    public List<Object[]> statsRevenueByEquipment() {
        return this.statsRepo.statsRevenueByEquipment();
    }

    @Override
    public List<Object[]> statsRevenueByYearMonth(int year, int month) {
        return this.statsRepo.statsRevenueByYearMonth(year, month);
    }

    @Override
    public List<Object[]> statsRevenueByRepairHistory() {
        return this.statsRepo.statsRevenueByRepairHistory();
    }

    @Override
    public List<Object[]> statsRevenueRepairHistoryByYearMonth(int year, int month) {
        return this.statsRepo.statsRevenueRepairHistoryByYearMonth(year, month);
    }
    
    
}
