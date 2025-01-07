/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ltd.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ltd.pojo.Equipment;
import com.ltd.pojo.Forum;
import com.ltd.pojo.Incident;
import com.ltd.pojo.Maintenanceschedule;
import com.ltd.pojo.User;
import com.ltd.service.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author Acer
 */
@Controller
@ControllerAdvice
public class HomeController {

    @Autowired
    private EquipmentService equipSer;

    @Autowired
    private CategoryService cateService;

    @Autowired
    private IncidentService incidentService;

    @Autowired
    private InvoiceDetailsService invoiceDetailSer;

    @Autowired
    private ForumService forumService;

    @Autowired
    private MaintenanceScheduleService maintenanceScheduleService;

    @Autowired
    private ForumViewService forumViewService;

    @ModelAttribute
    public void commonAttributes(Model model) {
        model.addAttribute("cates", this.cateService.getCates());
    }

    @GetMapping(value = "/login", produces = "text/html; charset=UTF-8")
    public String login() {
        return "login";
    }

    @RequestMapping(value = "/", produces = "text/html; charset=UTF-8")
    public String index(Model model, @RequestParam Map<String, String> params) throws JsonProcessingException {

        params.putIfAbsent("equipPage", "1");
        params.putIfAbsent("forumPage", "1");

        List<Maintenanceschedule> overdueAndUpcomingSchedules = this.maintenanceScheduleService.getOverdueAndUpcomingSchedules();
        model.addAttribute("schedules", overdueAndUpcomingSchedules);

        System.out.println("overdueAndUpcomingSchedules: " + overdueAndUpcomingSchedules);

        // Lấy danh sách thiết bị và số lượng thiết bị
        long equipmentCounter = this.equipSer.countAllEquipments();
        model.addAttribute("equipmentCounter", equipmentCounter);

        // Đếm số lượng thiết bị hư hỏng
        long incidentCounter = this.incidentService.countIncident();
        model.addAttribute("incidentCounter", incidentCounter);

        // Đếm tổng số thiết bị đã bán
        long totalSoldEquipment = this.invoiceDetailSer.countTotalSoldEquipment();
        model.addAttribute("totalSoldEquipment", totalSoldEquipment);

        //Mức độ hư hỏng
        Map<String, Double> severityPercentages = incidentService.calculateSeverityPercentages();

        // Sử dụng Jackson để chuyển đổi Map thành JSON
        ObjectMapper mapper = new ObjectMapper();
        String severityDataJson = mapper.writeValueAsString(severityPercentages);
        // Truyền JSON sang view
        model.addAttribute("severityData", severityDataJson);

        // Lấy danh sách thiết bị đang hoạt động
        params.put("currentStatus", "Hoạt động");
        List<Equipment> activeEquipments = this.equipSer.getEquipment(params);
        model.addAttribute("activeEquipments", activeEquipments);
        model.addAttribute("currentEquipPage", Integer.parseInt(params.get("equipPage")));

        // Lấy danh sách các bài viết trong diễn đàn (cần có forumService)
        long forumCounter = this.forumService.countAllForum();
        model.addAttribute("forumCounter", forumCounter);

        List<Forum> forumPosts = this.forumService.getPosts(params);
        model.addAttribute("forumPosts", forumPosts);
        model.addAttribute("currentForumPage", Integer.parseInt(params.get("forumPage")));

        // Lấy danh sách người xem cho mỗi bài viết
        List<List<User>> forumViewers = new ArrayList<>();
        for (Forum post : forumPosts) {
            if (post != null && post.getId() != null) {
                List<User> viewers = this.forumViewService.findUsersByForumId(post.getId());
                if (viewers != null && !viewers.isEmpty()) {
                    forumViewers.add(viewers); // Giữ lại để hiển thị
                }
            }

        }
        model.addAttribute("forumViewers", forumViewers);

        // Lấy danh sách thông báo và thêm vào model
        List<Incident> notifications = incidentService.getIncident();
        model.addAttribute("notifications", notifications);

        List<Incident> unnotifications = incidentService.getUnreadIncidents();
        int unreadCount = unnotifications.size();
        model.addAttribute("unreadCount", unreadCount);

        return "home";
    }

}
