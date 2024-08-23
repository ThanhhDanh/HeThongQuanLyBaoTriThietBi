/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ltd.service.impl;

import com.ltd.pojo.Invoicedetail;
import com.ltd.repository.InvoiceDetailsRepository;
import com.ltd.service.InvoiceDetailsService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Acer
 */
@Service
public class InvoiceDetailsServiceImpl implements InvoiceDetailsService{
    
    @Autowired
    private InvoiceDetailsRepository invoicedetailRepo;

    @Override
    public List<Invoicedetail> getInvoiceDetail() {
        return this.invoicedetailRepo.getInvoiceDetail();
    }

    @Override
    public Invoicedetail getInvoiceDetailById(int id) {
        return this.invoicedetailRepo.getInvoiceDetailById(id);
    }

    @Override
    public List<Invoicedetail> getInvoiceDetailByEquipmentId(int equipmentId) {
        return this.invoicedetailRepo.getInvoiceDetailByEquipmentId(equipmentId);
    }
    
}
