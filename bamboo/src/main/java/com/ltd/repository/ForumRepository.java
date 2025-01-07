/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.ltd.repository;

import com.ltd.pojo.Forum;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Acer
 */
public interface ForumRepository {

    Forum findById(int forumId);

    List<Forum> getPosts(Map<String, String> params);
    
    long countAllForum();
    
    List<Forum> getAllPosts();
    
    void addForum(Forum forum);
}
