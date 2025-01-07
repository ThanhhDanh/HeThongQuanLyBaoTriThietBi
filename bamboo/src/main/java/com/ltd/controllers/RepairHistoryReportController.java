/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ltd.controllers;

import com.ltd.pojo.Equipment;
import com.ltd.pojo.Repairhistory;
import com.ltd.service.EquipmentService;
import com.ltd.service.RepairHistoryService;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import org.apache.poi.ss.util.CellRangeAddress;

/**
 *
 * @author Acer
 */
@Controller
public class RepairHistoryReportController {

    @Autowired
    private EquipmentService equipmentService;

    @Autowired
    private RepairHistoryService repairHistoryService;

    @GetMapping(value = "/repairhistoryreport", produces = "text/html; charset=UTF-8")
    public String getRepairHistory(Model model, @RequestParam(value = "q", required = false) String query) {
        List<Equipment> equipmentList = equipmentService.getEquipment(null);
        List<Repairhistory> repairHistoryList = repairHistoryService.getRepairHistory();

        if (query != null && !query.isEmpty()) {
            equipmentList = equipmentList.stream()
                    .filter(e -> e.getName().toLowerCase().contains(query.toLowerCase()))
                    .collect(Collectors.toList());
        }

        model.addAttribute("equipmentList", equipmentList);
        model.addAttribute("repairHistory", repairHistoryList);
        return "repairhistoryreport";
    }

    @GetMapping("/exportRepairHistory")
    public void exportRepairHistory(HttpServletResponse response) throws IOException {
        // Tạo workbook và sheet
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Repair History");

        // Tạo tiêu đề
        Row titleRow = sheet.createRow(0);
        Cell titleCell = titleRow.createCell(0);
        titleCell.setCellValue("Báo cáo lịch sử sửa chữa");

        // Tạo style cho tiêu đề
        CellStyle titleStyle = workbook.createCellStyle();
        Font titleFont = workbook.createFont();
        titleFont.setBold(true);
        titleFont.setFontHeightInPoints((short) 16);
        titleStyle.setFont(titleFont);
        titleCell.setCellStyle(titleStyle);

        // Gộp các ô tiêu đề (từ cột 0 đến cột 5)
        sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 5));

        // Tạo header row
        Row headerRow = sheet.createRow(1);
        String[] headers = {"Ngày sửa chữa", "Tên thiết bị", "Loại sửa chữa", "Chi phí sửa chữa", "Lỗi hư hỏng", "Kỹ thuật viên"};
        for (int i = 0; i < headers.length; i++) {
            Cell cell = headerRow.createCell(i);
            cell.setCellValue(headers[i]);
        }

        // Lấy danh sách lịch sử sửa chữa
        List<Repairhistory> repairHistoryList = repairHistoryService.getRepairHistory();

        // Thêm dữ liệu vào sheet
        int rowNum = 2;
        for (Repairhistory history : repairHistoryList) {
            Row row = sheet.createRow(rowNum++);
            row.createCell(0).setCellValue(history.getRepairDate().toString());
            row.createCell(1).setCellValue(history.getEquipmentId().getName());
            row.createCell(2).setCellValue(history.getRepairType());
            row.createCell(3).setCellValue(history.getRepairCost().doubleValue());
            row.createCell(4).setCellValue(history.getIncidentId().getIncidentDescription());
            row.createCell(5).setCellValue(history.getPerformedByUserId().getUsername());
        }

        // Thiết lập kiểu file
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.setHeader("Content-Disposition", "attachment; filename=RepairHistory.xlsx");

        // Ghi workbook vào response output stream
        workbook.write(response.getOutputStream());
        workbook.close();
    }
}
