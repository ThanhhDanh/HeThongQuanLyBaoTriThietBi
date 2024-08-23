/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ltd.controllers;

import com.ltd.service.StatsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 *
 * @author Acer
 */
@Controller
public class StatsController {

    @Autowired
    private StatsService statsSer;

    @GetMapping("/stats")
    public String index(Model model,
            @RequestParam(value = "yearChart1", required = false) Integer yearChart1,
            @RequestParam(value = "monthChart1", required = false) Integer monthChart1,
            @RequestParam(value = "yearChart2", required = false) Integer yearChart2,
            @RequestParam(value = "monthChart2", required = false) Integer monthChart2) {

        if (yearChart1 != null && monthChart1 != null) {
            model.addAttribute("stats", this.statsSer.statsRevenueByYearMonth(yearChart1, monthChart1));
        } else {
            model.addAttribute("stats", this.statsSer.statsRevenueByEquipment());
        }

        if (yearChart2 != null && monthChart2 != null) {
            model.addAttribute("statsRepairHistory", this.statsSer.statsRevenueRepairHistoryByYearMonth(yearChart2, monthChart2));
        } else {
            model.addAttribute("statsRepairHistory", this.statsSer.statsRevenueByRepairHistory());
        }

        return "stats";
    }

}
