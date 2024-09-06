/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ltd.repository.impl;

import com.ltd.pojo.Incident;
import com.ltd.repository.IncidentRepository;
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
public class IncidentRepositoryImpl implements IncidentRepository {

    @Autowired
    private LocalSessionFactoryBean factory;

    @Override
    public List<Incident> getIncident() {
        Session s = this.factory.getObject().getCurrentSession();
        Query q = s.createQuery("From Incident");
        return q.getResultList();
    }

    @Override
    public Incident getIncidentById(int id) {
        Session s = this.factory.getObject().getCurrentSession();

        return s.get(Incident.class, id);
    }

    @Override
    public Incident getIncidentByEquipmentId(int equipmentId) {
        Session session = this.factory.getObject().getCurrentSession();
        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<Incident> query = builder.createQuery(Incident.class);
        Root<Incident> root = query.from(Incident.class);
        query.select(root).where(builder.equal(root.get("equipmentId").get("id"), equipmentId));

        return session.createQuery(query).uniqueResult();
    }

    @Override
    public void addOrUpdateIncident(Incident i) {
        Session s = this.factory.getObject().getCurrentSession();

        if (i.getId() != null) {
            s.update(i);
        } else {
            s.save(i);
        }
    }

}
