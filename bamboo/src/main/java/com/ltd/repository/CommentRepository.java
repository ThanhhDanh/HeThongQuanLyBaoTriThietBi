/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.ltd.repository;

import com.ltd.pojo.Comment;
import java.util.List;

/**
 *
 * @author Acer
 */
public interface CommentRepository {

    List<Comment> findByForumId(int forumId);

    void addComment(Comment comment);
}
