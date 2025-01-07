/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ltd.service.impl;

import com.ltd.pojo.Comment;
import com.ltd.repository.CommentRepository;
import com.ltd.service.CommentService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Acer
 */
@Service
public class CommentServiceImpl implements CommentService{
    
    @Autowired
    private CommentRepository commentRepo;

    @Override
    public List<Comment> getCommentsByForumId(int forumId) {
        return this.commentRepo.findByForumId(forumId);
    }

    @Override
    public void addComment(Comment comment) {
        this.commentRepo.addComment(comment);
    }
    
}
