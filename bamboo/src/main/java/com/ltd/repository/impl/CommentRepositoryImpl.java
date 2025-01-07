/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ltd.repository.impl;

import com.ltd.pojo.Comment;
import com.ltd.repository.CommentRepository;
import java.util.List;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Acer
 */
@Repository
@Transactional
public class CommentRepositoryImpl implements CommentRepository {

    @Autowired
    private LocalSessionFactoryBean factory;

    @Override
    public List<Comment> findByForumId(int forumId) {
        Session session = this.factory.getObject().getCurrentSession();
        Query<Comment> query = session.createQuery(
                "FROM Comment c WHERE c.forumId.id = :forumId", Comment.class);
        query.setParameter("forumId", forumId);
        return query.getResultList();
    }

    @Override
    public void addComment(Comment comment) {
        Session s = this.factory.getObject().getCurrentSession();
        s.save(comment);
    }

}
