/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ltd.repository.impl;

import com.ltd.pojo.Invoicedetail;
import com.ltd.repository.InvoiceDetailsRepository;
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
public class InvoiceDetailsRepositoryImpl implements InvoiceDetailsRepository {

    @Autowired
    private LocalSessionFactoryBean factory;

    @Override
    public List<Invoicedetail> getInvoiceDetail() {
        Session s = this.factory.getObject().getCurrentSession();
        Query q = s.createQuery("From Invoicedetail");
        return q.getResultList();
    }

    @Override
    public Invoicedetail getInvoiceDetailById(int id) {
        Session s = this.factory.getObject().getCurrentSession();

        return s.get(Invoicedetail.class, id);
    }

    @Override
    public List<Invoicedetail> getInvoiceDetailByEquipmentId(int equipmentId) {
        Session session = this.factory.getObject().getCurrentSession();
        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<Invoicedetail> query = builder.createQuery(Invoicedetail.class);
        Root<Invoicedetail> root = query.from(Invoicedetail.class);
        query.select(root).where(builder.equal(root.get("equipmentId"), equipmentId));

        return session.createQuery(query).getResultList();
    }

}
