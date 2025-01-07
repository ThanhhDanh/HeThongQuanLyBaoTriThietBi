/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ltd.repository.impl;

import com.ltd.pojo.Equipment;
import com.ltd.pojo.Repairhistory;
import com.ltd.repository.RepairHistoryRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Predicate;
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
public class RepairHistoryRepositoryImpl implements RepairHistoryRepository {

    @Autowired
    private LocalSessionFactoryBean factory;

    @Override
    public List<Repairhistory> getRepairHistory() {
        Session s = this.factory.getObject().getCurrentSession();
        Query q = s.createQuery("From Repairhistory");
        return q.getResultList();
    }

    @Override
    public Repairhistory getRepairHistoryById(int id) {
        Session s = this.factory.getObject().getCurrentSession();

        return s.get(Repairhistory.class, id);
    }

    @Override
    public List<Repairhistory> getRepairHistoriesByEquipmentId(int equipmentId) {
        Session session = this.factory.getObject().getCurrentSession();
        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<Repairhistory> query = builder.createQuery(Repairhistory.class);
        Root<Repairhistory> root = query.from(Repairhistory.class);
        query.select(root).where(builder.equal(root.get("equipmentId").get("id"), equipmentId));

        return session.createQuery(query).getResultList();
    }

    @Override
    public List<Repairhistory> getRepairHistoriesByIncidentId(int incidentId) {
        Session session = this.factory.getObject().getCurrentSession();
        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<Repairhistory> query = builder.createQuery(Repairhistory.class);
        Root<Repairhistory> root = query.from(Repairhistory.class);
        query.select(root).where(builder.equal(root.get("incidentId").get("id"), incidentId));

        return session.createQuery(query).getResultList();
    }

    @Override
    public void addOrUpdateRepairHistory(Repairhistory rh) {
        Session s = this.factory.getObject().getCurrentSession();

        if (rh.getId() != null) {
            s.update(rh);
        } else {
            s.save(rh);
        }
    }

}
