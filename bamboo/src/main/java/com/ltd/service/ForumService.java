/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.ltd.service;

import com.ltd.pojo.Comment;
import com.ltd.pojo.Forum;
import com.ltd.pojo.ForumDTO;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Acer
 */
public interface ForumService {

    List<Forum> getPosts(Map<String, String> params);

    Forum findById(int forumId);

    long countAllForum();
    
    ForumDTO getForumWithComments(int forumId);
    
    List<Forum> getAllPosts();
    
    void addForum(Forum forum);
    
    List<Comment> getCommentsByForumId(int forumId);
}
