/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ltd.repository.impl;

import com.ltd.pojo.User;
import com.ltd.repository.ForumViewRepository;
import com.ltd.repository.UserRepository;
import java.util.List;
import javax.persistence.NoResultException;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Acer
 */
@Repository
@Transactional
public class UserRepositoryImpl implements UserRepository {

    @Autowired
    private LocalSessionFactoryBean factory;

    @Autowired
    private BCryptPasswordEncoder passEncoder;

    @Autowired
    private ForumViewRepository forumViewRepository;

    @Override
    public User getUserByUsername(String username) {
        Session s = this.factory.getObject().getCurrentSession();
        Query<User> q = s.createNamedQuery("User.findByUsername", User.class);
        q.setParameter("username", username);

        return (User) q.getSingleResult();
    }

    @Override
    public List<User> getAllUsers() {
        Session session = this.factory.getObject().getCurrentSession();
        Query<User> query = session.createQuery("FROM User", User.class);
        return query.getResultList();
    }

    @Override
    public List<User> getUsersByRole(String role) {
        Session session = this.factory.getObject().getCurrentSession();
        Query<User> query = session.createQuery("FROM User u WHERE u.role = :role", User.class);
        query.setParameter("role", role);
        return query.getResultList();
    }

    @Override
    public boolean authUser(String username, String password) {
        try {
            User u = this.getUserByUsername(username);
            return this.passEncoder.matches(password, u.getPassword());
        } catch (NoResultException e) {
            System.err.println("Lỗi authUser: " + e.getMessage());
            return false; // Trả về false nếu không tìm thấy người dùng
        }
    }

    @Override
    public User addUser(User u) {
        Session s = this.factory.getObject().getCurrentSession();
        s.save(u);

        return u;
    }

    @Override
    public List<User> getUsersViewedForum(int forumId) {
        return forumViewRepository.findUsersByForumId(forumId);
    }

    @Override
    public User getUserById(Integer id) {
        Session session = this.factory.getObject().getCurrentSession();
        return session.get(User.class, id);
    }

    @Override
    public User updateUser(User u) {
        Session session = this.factory.getObject().getCurrentSession();
        session.update(u);
        return u;
    }

    @Override
    public User findByEmail(String email) {
        Session session = this.factory.getObject().getCurrentSession();
        Query<User> query = session.createQuery("FROM User u WHERE u.email = :email", User.class);
        query.setParameter("email", email);

        try {
            return query.getSingleResult();
        } catch (NoResultException e) {
            System.err.println("Không tìm thấy người dùng với email: " + email);
            return null;
        }
    }

    @Override
    public void updatePassword(User user) {
        Session session = this.factory.getObject().getCurrentSession();
        Query query = session.createQuery("UPDATE User u SET u.password = :password WHERE u.id = :userId");
        query.setParameter("password", user.getPassword());
        query.setParameter("userId", user.getId());
        query.executeUpdate();
    }
}
