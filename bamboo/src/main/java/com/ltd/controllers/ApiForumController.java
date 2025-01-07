/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ltd.controllers;

import com.ltd.pojo.Forum;
import com.ltd.service.ForumService;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author Acer
 */
@RestController
@RequestMapping("/api")
@CrossOrigin
public class ApiForumController {
    @Autowired
    private ForumService forumService;

    @GetMapping("/forums")
    public ResponseEntity<Map<String, Object>> list(@RequestParam Map<String, String> params) {
        List<Forum> forum = this.forumService.getPosts(params);

        long totalCount = this.forumService.countAllForum();

        Map<String, Object> response = new HashMap<>();
        response.put("data", forum);
        response.put("totalCount", totalCount);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
