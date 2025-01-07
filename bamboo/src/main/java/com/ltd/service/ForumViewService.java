/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.ltd.service;

import com.ltd.pojo.Forum;
import com.ltd.pojo.ForumView;
import com.ltd.pojo.User;
import java.util.List;

/**
 *
 * @author Acer
 */
public interface ForumViewService {

    List<User> findUsersByForumId(int forumId);

    void saveForumView(Forum forum, User user);
    
    List<User> getViewers(int forumId);
    
    ForumView findByForumAndUser(Forum forum, User user);
}
