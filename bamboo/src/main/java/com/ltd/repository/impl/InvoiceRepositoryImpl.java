/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ltd.repository.impl;

import com.ltd.pojo.Invoice;
import com.ltd.repository.InvoiceRepository;
import java.io.Serializable;
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
public class InvoiceRepositoryImpl implements InvoiceRepository {

    @Autowired
    private LocalSessionFactoryBean factory;

    @Override
    public List<Invoice> getInvoice() {
        Session s = this.factory.getObject().getCurrentSession();
        Query q = s.createQuery("From Invoice");
        return q.getResultList();
    }

    @Override
    public Invoice findById(Integer id) {
        Session session = this.factory.getObject().getCurrentSession();
        return session.get(Invoice.class, id);
    }

    @Override
    public void save(Invoice invoice) {
        Session s = this.factory.getObject().getCurrentSession();
        s.save(invoice);
    }

    @Override
    public List<Invoice> findInvoicesByUserId(int userId) {
        Session session = this.factory.getObject().getCurrentSession();
        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<Invoice> query = builder.createQuery(Invoice.class);
        Root<Invoice> root = query.from(Invoice.class);

        query.select(root).where(builder.equal(root.get("userId"), userId));

        return session.createQuery(query).getResultList();
    }
}
