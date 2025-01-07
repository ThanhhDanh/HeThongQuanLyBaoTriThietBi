/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.ltd.repository;

import com.ltd.pojo.Invoice;
import java.util.List;

/**
 *
 * @author Acer
 */
public interface InvoiceRepository {
    List<Invoice> getInvoice();
    Invoice findById(Integer id);
    void save(Invoice invoice);
    List<Invoice> findInvoicesByUserId(int userId);
}
