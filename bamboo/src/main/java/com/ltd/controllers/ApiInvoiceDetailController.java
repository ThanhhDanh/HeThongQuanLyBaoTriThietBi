/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ltd.controllers;

import com.ltd.pojo.Invoicedetail;
import com.ltd.service.InvoiceDetailsService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author Acer
 */
@RestController
@RequestMapping("/api")
@CrossOrigin
public class ApiInvoiceDetailController {

    @Autowired
    private InvoiceDetailsService invoiceDetailService;

    @GetMapping("/invoicedetail/{invoiceId}")
    public ResponseEntity<List<Invoicedetail>> getInvoiceDetailsByInvoiceId(@PathVariable Integer invoiceId) {
        List<Invoicedetail> details = invoiceDetailService.findInvoiceDetailsByInvoiceId(invoiceId);
        return new ResponseEntity<>(details, HttpStatus.OK);
    }
}
