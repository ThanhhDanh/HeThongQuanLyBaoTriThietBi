/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ltd.service.impl;

import com.ltd.converters.ForumConverter;
import com.ltd.pojo.Comment;
import com.ltd.pojo.Forum;
import com.ltd.pojo.ForumDTO;
import com.ltd.repository.CommentRepository;
import com.ltd.repository.ForumRepository;
import com.ltd.service.ForumService;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Acer
 */
@Service
@Transactional
public class ForumServiceImpl implements ForumService {

    @Autowired
    private ForumRepository forumRepository;

    @Autowired
    private CommentRepository commentRepository;

    @Override
    public List<Forum> getPosts(Map<String, String> params) {
        return this.forumRepository.getPosts(params);
    }

    @Override
    public long countAllForum() {
        return this.forumRepository.countAllForum();
    }

    @Override
    public Forum findById(int forumId) {
        return this.forumRepository.findById(forumId);
    }

    @Override
    public ForumDTO getForumWithComments(int forumId) {
        Forum forum = forumRepository.findById(forumId);

        if (forum != null) {
            // Nạp các comment liên quan
            List<Comment> comments = commentRepository.findByForumId(forumId);
            forum.setCommentCollection(new HashSet<>(comments)); // Cập nhật commentCollection nếu cần

            // Chuyển đổi sang DTO
            return ForumConverter.toDTO(forum);
        }
        return null;
    }

    @Override
    public List<Forum> getAllPosts() {
        return this.forumRepository.getAllPosts();
    }

    @Override
    public void addForum(Forum forum) {
        this.forumRepository.addForum(forum);
    }

    @Override
    public List<Comment> getCommentsByForumId(int forumId) {
        return this.commentRepository.findByForumId(forumId);
    }
}
