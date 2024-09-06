/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ltd.repository.impl;

import com.ltd.controllers.PaginationUtils;
import com.ltd.pojo.Equipment;
import com.ltd.repository.EquipmentRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
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
public class EquipmentRepositoryImpl implements EquipmentRepository {

    @Autowired
    private LocalSessionFactoryBean factory;

    @Override
    public List<Equipment> getEquipment(Map<String, String> params) {
        Session s = this.factory.getObject().getCurrentSession();

        CriteriaBuilder b = s.getCriteriaBuilder();
        CriteriaQuery<Equipment> q = b.createQuery(Equipment.class);
        Root root = q.from(Equipment.class);
        q.select(root);

        if (params != null) {
            List<Predicate> predicates = new ArrayList<>();

            String key = params.get("q");
            if (key != null && !key.isEmpty()) {
                Predicate p1 = b.like(root.get("name"), String.format("%%%s%%", key));
                predicates.add(p1);
            }

//            String fromPrice = params.get("fromPrice");
//            if (fromPrice != null && !fromPrice.isEmpty()) {
//                Predicate p2 = b.greaterThanOrEqualTo(root.get("price"), Double.parseDouble(fromPrice));
//                predicates.add(p2);
//            }
//            String toPrice = params.get("toPrice");
//            if (toPrice != null && !toPrice.isEmpty()) {
//                Predicate p3 = b.lessThanOrEqualTo(root.get("price"), Double.parseDouble(toPrice));
//                predicates.add(p3);
//            }
            String find = params.get("find");
            if (find != null && !find.isEmpty()) {
                Predicate p4 = b.like(root.get("manufacturer"), String.format("%%%s%%", find));
                predicates.add(p4);
            }

            String cateId = params.get("cateId");
            if (cateId != null && !cateId.isEmpty()) {
                Predicate p5 = b.equal(root.get("categoryId"),
                        Integer.parseInt(cateId)); //root.get("category").as(Integer.class) hibernate 6 trở lên dùng cái này
                predicates.add(p5);
            }
            q.where(predicates.toArray(Predicate[]::new));
        }

        Query query = s.createQuery(q);

        if (params != null) {
            String pageParam = params.get("page");
            int page = pageParam != null && !pageParam.isEmpty() ? Integer.parseInt(pageParam) : 1;
            if (page > 0) {
                PaginationUtils.applyPagination(query, page, 9);  // maxResults là 9
            }
        }

        return query.getResultList();

    }

    @Override
    public long countAllEquipments() {
        Session s = this.factory.getObject().getCurrentSession();
        CriteriaBuilder b = s.getCriteriaBuilder();
        CriteriaQuery<Long> q = b.createQuery(Long.class);
        Root<Equipment> root = q.from(Equipment.class);
        q.select(b.count(root));

        return s.createQuery(q).getSingleResult();
    }

    @Override
    public void addOrUpdate(Equipment p) {
        Session s = this.factory.getObject().getCurrentSession();

        if (p.getId() != null) {
            s.update(p);
        } else {
            s.save(p);
        }

    }

    @Override
    public Equipment getEquipmentById(int id) {
        Session s = this.factory.getObject().getCurrentSession();

        return s.get(Equipment.class, id);

    }

    @Override
    public void deleteEquipment(int id) {
        Session s = this.factory.getObject().getCurrentSession();
        Equipment e = this.getEquipmentById(id);
        s.delete(e);
    }

}
