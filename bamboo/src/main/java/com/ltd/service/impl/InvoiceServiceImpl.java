/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ltd.service.impl;

import com.ltd.pojo.Invoice;
import com.ltd.repository.InvoiceRepository;
import com.ltd.service.InvoiceService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Acer
 */
@Service
public class InvoiceServiceImpl implements InvoiceService {

    @Autowired
    private InvoiceRepository invoiceRepository;

    @Override
    public Invoice findById(String id) {
        return invoiceRepository.findById(Integer.parseInt(id));
    }

    @Override
    public void save(Invoice invoice) {
        invoiceRepository.save(invoice);
    }

    @Override
    public List<Invoice> getInvoice() {
        return invoiceRepository.getInvoice();
    }

    @Override
    public List<Invoice> findInvoicesByUserId(int userId) {
        return this.invoiceRepository.findInvoicesByUserId(userId);
    }

}
