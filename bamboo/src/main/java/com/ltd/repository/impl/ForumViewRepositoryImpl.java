/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ltd.repository.impl;

import com.ltd.pojo.Forum;
import com.ltd.pojo.ForumView;
import com.ltd.pojo.User;
import com.ltd.repository.ForumViewRepository;
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
public class ForumViewRepositoryImpl implements ForumViewRepository {

    @Autowired
    private LocalSessionFactoryBean factory;

    @Override
    public List<User> findUsersByForumId(int forumId) {
        Session session = this.factory.getObject().getCurrentSession();
        // Sử dụng JOIN để lấy đối tượng User từ ForumView
        Query<User> query = session.createQuery(
                "SELECT u FROM User u JOIN ForumView fv ON u.id = fv.userId WHERE fv.forumId.id = :forumId", User.class);
        query.setParameter("forumId", forumId);
        return query.getResultList();
    }

    @Override
    public ForumView getForumViewById(int id) {
        Session session = this.factory.getObject().getCurrentSession();
        return session.get(ForumView.class, id);  // Tìm ForumView bằng ID
    }

    @Override
    public ForumView findByForumAndUser(Forum forum, User user) {
        Session session = this.factory.getObject().getCurrentSession();
        Query<ForumView> query = session.createQuery(
                "FROM ForumView fv WHERE fv.forumId = :forum AND fv.userId = :user", ForumView.class);
        query.setParameter("forum", forum);
        query.setParameter("user", user);

        return query.uniqueResult();
    }

    @Override
    public void save(ForumView forumView) {
        Session session = this.factory.getObject().getCurrentSession();
        session.saveOrUpdate(forumView);  // Lưu hoặc cập nhật ForumView
    }
}
