/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ltd.repository.impl;

import com.ltd.pojo.Equipment;
import com.ltd.pojo.Invoice;
import com.ltd.pojo.Invoicedetail;
import com.ltd.pojo.Repairhistory;
import com.ltd.repository.StatsRepository;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
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
public class StatsRepositoryImpl implements StatsRepository {

    @Autowired
    private LocalSessionFactoryBean factory;

    @Override
    public List<Object[]> statsRevenueByEquipment() {
        Session s = this.factory.getObject().getCurrentSession();
        CriteriaBuilder b = s.getCriteriaBuilder();
        CriteriaQuery<Object[]> q = b.createQuery(Object[].class);

        Root rP = q.from(Equipment.class);
        Root rD = q.from(Invoicedetail.class);
        Root rC = q.from(Invoice.class);

        q.where(b.and(
                b.equal(rP.get("id"), rD.get("equipmentId")),
                b.equal(rC.get("id"), rD.get("invoiceId"))
        ));

        q.multiselect(
                rP.get("id"),
                rP.get("code"),
                rP.get("name"),
                b.sum(rD.get("quantity")),
                b.sum(b.prod(rD.get("quantity"), rD.get("unitPrice"))),
                rC.get("id"),
                rC.get("createdDate")
        );
        q.groupBy(rP.get("id"), rP.get("name"), rC.get("id"));

        Query query = s.createQuery(q);

        return query.getResultList();

    }

    @Override
    public List<Object[]> statsRevenueByYearMonth(int year, int month) {
        Session s = this.factory.getObject().getCurrentSession();
        CriteriaBuilder b = s.getCriteriaBuilder();
        CriteriaQuery<Object[]> q = b.createQuery(Object[].class);

        Root rP = q.from(Equipment.class);
        Root rO = q.from(Invoice.class);
        Root rD = q.from(Invoicedetail.class);

        List<Predicate> predicates = new ArrayList<>();

        predicates.add(b.equal(rO.get("id"), rD.get("invoiceId").get("id")));
        predicates.add(b.equal(rP.get("id"), rD.get("equipmentId").get("id")));

        if (year != 0) {
            predicates.add(b.equal(b.function("YEAR", Integer.class, rO.get("createdDate")), year));
        }
        if (month != 0) {
            predicates.add(b.equal(b.function("MONTH", Integer.class, rO.get("createdDate")), month));
        }

        q.where(predicates.toArray(new Predicate[0]));

//        q.where(
//                b.equal(rO.get("id"), rD.get("invoiceId").get("id")),
//                b.equal(b.function("YEAR", Integer.class, rO.get("createdDate")), year),
//                b.equal(b.function("MONTH", Integer.class, rO.get("createdDate")), month),
//                b.equal(rP.get("id"), rD.get("equipmentId").get("id"))
//        );
//        q.multiselect(b.function(month, Integer.class, rO.get("createdDate")),
//                b.sum(b.prod(rD.get("quantity"), rD.get("unitPrice"))));
//        q.groupBy(b.function(month, Integer.class, rO.get("createdDate")));
        q.multiselect(
                rP.get("id"),
                rP.get("code"),
                rP.get("name"),
                b.sum(rD.get("quantity")),
                b.sum(b.prod(rD.get("quantity"), rD.get("unitPrice"))),
                rO.get("id"),
                rO.get("createdDate")
        );
        q.groupBy(rP.get("id"), rP.get("name"), rO.get("id"));

        Query query = s.createQuery(q);

        return query.getResultList();
    }

    @Override
    public List<Object[]> statsRevenueByRepairHistory() {
        Session s = this.factory.getObject().getCurrentSession();
        CriteriaBuilder b = s.getCriteriaBuilder();
        CriteriaQuery<Object[]> q = b.createQuery(Object[].class);

        Root rP = q.from(Equipment.class);
        Root rR = q.from(Repairhistory.class);

        q.where(
                b.equal(rP.get("id"), rR.get("equipmentId"))
        );

        q.multiselect(
                rP.get("id"),
                rP.get("code"),
                rP.get("name"),
                rR.get("repairDate"),
                b.sum(rR.get("repairCost"))
        );
        q.groupBy(rP.get("id"), rP.get("code"), rP.get("name"), rR.get("repairDate"));

        Query query = s.createQuery(q);

        return query.getResultList();
    }

    @Override
    public List<Object[]> statsRevenueRepairHistoryByYearMonth(int year, int month) {
        Session s = this.factory.getObject().getCurrentSession();
        CriteriaBuilder b = s.getCriteriaBuilder();
        CriteriaQuery<Object[]> q = b.createQuery(Object[].class);

        Root rP = q.from(Equipment.class);
        Root rR = q.from(Repairhistory.class);

        List<Predicate> predicates = new ArrayList<>();

        predicates.add(b.equal(rP.get("id"), rR.get("equipmentId").get("id")));

        if (year != 0) {
            predicates.add(b.equal(b.function("YEAR", Integer.class, rR.get("repairDate")), year));
        }

        if (month != 0) {
            predicates.add(b.equal(b.function("MONTH", Integer.class, rR.get("repairDate")), month));
        }

        q.where(predicates.toArray(new Predicate[0]));

        q.multiselect(
                rP.get("id"),
                rP.get("code"),
                rP.get("name"),
                rR.get("repairDate"),
                b.sum(rR.get("repairCost"))
        );
        q.groupBy(rP.get("id"), rP.get("code"), rP.get("name"), rR.get("repairDate"));

        Query query = s.createQuery(q);

        return query.getResultList();
    }

}
