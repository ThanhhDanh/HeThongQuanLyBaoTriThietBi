/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.ltd.service;

import java.util.List;

/**
 *
 * @author Acer
 */
public interface StatsService {
    List<Object[]> statsRevenueByEquipment();
    List<Object[]> statsRevenueByYearMonth(int year, int month);
    List<Object[]> statsRevenueByRepairHistory();
    List<Object[]> statsRevenueRepairHistoryByYearMonth(int year, int month);
}
