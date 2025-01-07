/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ltd.repository.impl;

import com.ltd.controllers.PaginationUtils;
import com.ltd.pojo.Maintenanceschedule;
import com.ltd.repository.MaintenanceScheduleRepository;
import java.time.LocalDate;
import java.time.ZoneId;
import java.sql.Date;
import java.util.List;
import java.util.stream.Collectors;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Acer
 */
@Repository
@Transactional
public class MaintenanceScheduleRepositoryImpl implements MaintenanceScheduleRepository {

    @Autowired
    private LocalSessionFactoryBean factory;

    @Override
    public List<Maintenanceschedule> getMaintenanceschedule(int page, int maxResults) {
        Session s = this.factory.getObject().getCurrentSession();
        Query q = s.createQuery("From Maintenanceschedule");

        PaginationUtils.applyPagination(q, page, maxResults);

        return q.getResultList();
    }

    @Override
    public long countAllMaintenanceSchedule() {
        Session s = this.factory.getObject().getCurrentSession();
        CriteriaBuilder b = s.getCriteriaBuilder();
        CriteriaQuery<Long> q = b.createQuery(Long.class);
        Root<Maintenanceschedule> root = q.from(Maintenanceschedule.class);
        q.select(b.count(root));

        return s.createQuery(q).getSingleResult();
    }

    @Override
    public String getMaintenanceStatus(Maintenanceschedule maintenanceSchedule) {
        LocalDate today = LocalDate.now();

        // Chuyển đổi từ java.util.Date sang LocalDate
        LocalDate nextMaintenanceDate = convertToLocalDate((Date) maintenanceSchedule.getNextMaintenanceDate());
        LocalDate reminderDate = convertToLocalDate((Date) maintenanceSchedule.getReminderDate());

        if (today.isAfter(nextMaintenanceDate)) {
            return "Đã quá hạn";
        } else if (today.isAfter(reminderDate) && today.isBefore(nextMaintenanceDate)) {
            return "Sắp đến hạn";
        } else {
            return "Đang xử lý";
        }
    }

    private LocalDate convertToLocalDate(Date date) {
        if (date == null) {
            return null; // Hoặc xử lý trường hợp ngày null tùy theo nhu cầu
        }
        return date.toLocalDate();
    }

    @Override
    public Maintenanceschedule getMaintenancescheduleById(int id) {
        Session s = this.factory.getObject().getCurrentSession();

        return s.get(Maintenanceschedule.class, id);
    }

    @Override
    public List<Maintenanceschedule> getMaintenancescheduleByEquipmentId(int equipmentId) {
        Session session = this.factory.getObject().getCurrentSession();
        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<Maintenanceschedule> query = builder.createQuery(Maintenanceschedule.class);
        Root<Maintenanceschedule> root = query.from(Maintenanceschedule.class);
        query.select(root).where(builder.equal(root.get("equipmentId").get("id"), equipmentId));

        return session.createQuery(query).getResultList();
    }

    @Override
    public void addOrUpdateMaintenanceScheduleService(Maintenanceschedule maintenanceSchedule) {
        Session s = this.factory.getObject().getCurrentSession();

        if (maintenanceSchedule.getId() != null) {
            s.update(maintenanceSchedule);
        } else {
            s.save(maintenanceSchedule);
        }
    }

    //Tạo thông báo khi đăng nhập
    @Override
    public List<Maintenanceschedule> getOverdueAndUpcomingSchedules() {
        Session session = this.factory.getObject().getCurrentSession();
        LocalDate today = LocalDate.now();
        Date sqlToday = Date.valueOf(today); // Ngày hiện tại
        Date sqlUpcomingDate = Date.valueOf(today.plusDays(7)); // 7 ngày tới

        // Truy vấn để lấy các lịch bảo trì
        Query query = session.createQuery("FROM Maintenanceschedule ms "
                + "WHERE ms.nextMaintenanceDate < :today "
                + "OR (ms.reminderDate <= :today AND ms.nextMaintenanceDate > :today)");
        query.setParameter("today", sqlToday);

        List<Maintenanceschedule> schedules = query.getResultList();

        // Kiểm tra trạng thái của từng lịch bảo trì
        schedules.forEach(ms -> {
            String status = getMaintenanceStatus(ms); // Gọi phương thức để lấy trạng thái
            ms.setMaintenanceStatus(status); // Cập nhật trạng thái của lịch bảo trì
        });

        // Lọc chỉ những lịch có trạng thái "Đã quá hạn" hoặc "Sắp đến hạn"
        return schedules.stream()
                .filter(ms -> ms.getMaintenanceStatus().equals("Đã quá hạn")
                || ms.getMaintenanceStatus().equals("Sắp đến hạn"))
                .collect(Collectors.toList());
    }

    @Override
    public Maintenanceschedule findTopByEquipmentIdOrderByMaintenanceDateDesc(int equipmentId) {
        Session session = this.factory.getObject().getCurrentSession();
        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<Maintenanceschedule> query = builder.createQuery(Maintenanceschedule.class);
        Root<Maintenanceschedule> root = query.from(Maintenanceschedule.class);

        //Điều kiện lọc theo equipment và sắp xếp theo maintenanceDate giảm dần
        query.select(root)
                .where(builder.equal(root.get("equipmentId").get("id"), equipmentId))
                .orderBy(builder.desc(root.get("maintenanceDate")));

        //Lấy kết quả đầu tiên
        return session.createQuery(query).setMaxResults(1).uniqueResult();
    }

}
