/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.ltd.service;

import com.ltd.pojo.Invoice;
import java.util.List;

/**
 *
 * @author Acer
 */
public interface InvoiceService {
    List<Invoice> getInvoice();
    Invoice findById(String id);
    void save(Invoice invoice);
    List<Invoice> findInvoicesByUserId(int userId);
}
