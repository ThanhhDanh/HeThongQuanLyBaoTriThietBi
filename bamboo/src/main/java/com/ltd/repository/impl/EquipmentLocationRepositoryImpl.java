/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ltd.repository.impl;

import com.ltd.pojo.Equipmentlocation;
import com.ltd.repository.EquipmentLocationRepository;
import java.util.List;
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
public class EquipmentLocationRepositoryImpl implements EquipmentLocationRepository {

    @Autowired
    private LocalSessionFactoryBean factory;

    @Override
    public List<Equipmentlocation> getEquipLocation() {
        Session s = this.factory.getObject().getCurrentSession();
        Query q = s.createQuery("From Equipmentlocation");
        return q.getResultList();
    }

    @Override
    public Equipmentlocation getEquipmentLocationById(int id) {
        Session s = this.factory.getObject().getCurrentSession();

        return s.get(Equipmentlocation.class, id);
    }

    @Override
    public Equipmentlocation getEquipmentLocationByEquipmentId(int equipmentId) {
        Session session = this.factory.getObject().getCurrentSession();
        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<Equipmentlocation> query = builder.createQuery(Equipmentlocation.class);
        Root<Equipmentlocation> root = query.from(Equipmentlocation.class);
        query.select(root).where(builder.equal(root.get("equipmentId").get("id"), equipmentId));

        return session.createQuery(query).uniqueResult();
    }

    @Override
    public void updateEquipmentLocation(Equipmentlocation equipLocation) {
        Session s = this.factory.getObject().getCurrentSession();

        if (equipLocation.getId() != null) {
            s.update(equipLocation);
        } else {
            s.save(equipLocation);
        }
    }

}
