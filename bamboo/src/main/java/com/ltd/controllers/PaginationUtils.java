/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ltd.controllers;

import javax.persistence.Query;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Controller;

/**
 *
 * @author Acer
 */
@Controller
public class PaginationUtils {

    public static void applyPagination(Query query, int page, int maxResults) {
        if (page > 0) {
            int start = (page - 1) * maxResults;
            query.setFirstResult(start);
            query.setMaxResults(maxResults);
        }
    }
}
