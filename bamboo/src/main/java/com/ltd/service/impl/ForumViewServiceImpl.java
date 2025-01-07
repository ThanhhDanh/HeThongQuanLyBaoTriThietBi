/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ltd.service.impl;

import com.ltd.pojo.Forum;
import com.ltd.pojo.ForumView;
import com.ltd.pojo.User;
import com.ltd.repository.ForumViewRepository;
import com.ltd.service.ForumViewService;
import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Acer
 */
@Service
public class ForumViewServiceImpl implements ForumViewService {

    @Autowired
    private ForumViewRepository forumViewRepo;

    @Override
    public List<User> findUsersByForumId(int forumId) {
        return this.forumViewRepo.findUsersByForumId(forumId);
    }

    @Override
    public void saveForumView(Forum forum, User user) {
        // Kiểm tra xem người dùng đã xem forum này chưa
        ForumView existingView = forumViewRepo.findByForumAndUser(forum, user);
        if (existingView == null) {
            // Nếu chưa, thêm người xem mới
            ForumView forumView = new ForumView();
            forumView.setForumId(forum);
            forumView.setUserId(user);
            forumView.setViewedDate(new Date());
            forumViewRepo.save(forumView);
        }
    }

    @Override
    public List<User> getViewers(int forumId) {
        return this.forumViewRepo.findUsersByForumId(forumId);
    }

    @Override
    public ForumView findByForumAndUser(Forum forum, User user) {
        return this.forumViewRepo.findByForumAndUser(forum, user);
    }

}
