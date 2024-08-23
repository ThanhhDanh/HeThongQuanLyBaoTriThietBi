/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.ltd.repository;


import com.ltd.pojo.Invoicedetail;
import java.util.List;

/**
 *
 * @author Acer
 */
public interface InvoiceDetailsRepository {
    List<Invoicedetail> getInvoiceDetail();
    Invoicedetail getInvoiceDetailById(int id);
    List<Invoicedetail> getInvoiceDetailByEquipmentId(int equipmentId);
}
