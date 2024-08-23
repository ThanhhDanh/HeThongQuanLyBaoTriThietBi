/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ltd.repository.impl;

import com.ltd.pojo.Equipmentimage;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import com.ltd.repository.EquipmentImageRepository;
import java.util.ArrayList;
import java.util.Map;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import org.hibernate.Session;

/**
 *
 * @author Acer
 */
@Repository
@Transactional
public class EquipmentImageRepositoryImpl implements EquipmentImageRepository {

    @Autowired
    private LocalSessionFactoryBean factory;

    @Override
    public List<Equipmentimage> getEquipImage(Map<String, String> params) {
        Session s = this.factory.getObject().getCurrentSession();
        CriteriaBuilder b = s.getCriteriaBuilder();
        CriteriaQuery<Equipmentimage> q = b.createQuery(Equipmentimage.class);
        Root root = q.from(Equipmentimage.class);
        q.select(root);
        
        if(params != null) {
            List<Predicate> predicates = new ArrayList<>();
            String equipmentId = params.get("equipmentId");
            if (equipmentId != null && !equipmentId.isEmpty()) {
                Predicate p2 = b.equal(root.get("equipmentId"),
                        Integer.parseInt(equipmentId)); //root.get("category").as(Integer.class) hibernate 6 trở lên dùng cái này
                predicates.add(p2);
            }
            q.where(predicates.toArray(Predicate[]::new));
        }
        
        Query query = s.createQuery(q);

        return query.getResultList();
    }

    @Override
    public Equipmentimage getEquipmentImgById(int id) {
        Session s = this.factory.getObject().getCurrentSession();

        return s.get(Equipmentimage.class, id);

    }

    @Override
    public Equipmentimage getEquipmentImgByEquipmentId(int equipmentId) {
        Session session = this.factory.getObject().getCurrentSession();
        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<Equipmentimage> query = builder.createQuery(Equipmentimage.class);
        Root<Equipmentimage> root = query.from(Equipmentimage.class);
        query.select(root).where(builder.equal(root.get("equipmentId").get("id"), equipmentId));

        return session.createQuery(query).uniqueResult();
    }

    @Override
    public void saveEquipImage(Equipmentimage equipmentimage) {
        Session s = this.factory.getObject().getCurrentSession();

        if (equipmentimage.getId() != null) {
            s.update(equipmentimage);
        } else {
            s.save(equipmentimage);
        }
    }

}
