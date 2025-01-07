/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.ltd.repository;

import com.ltd.pojo.Forum;
import com.ltd.pojo.ForumView;
import com.ltd.pojo.User;
import java.util.List;

/**
 *
 * @author Acer
 */
public interface ForumViewRepository {

    List<User> findUsersByForumId(int forumId);

    ForumView getForumViewById(int id);
    
    ForumView findByForumAndUser(Forum forum, User user);
    
    void save(ForumView forumView);
    
}
