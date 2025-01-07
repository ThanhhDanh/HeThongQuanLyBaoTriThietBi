/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ltd.repository.impl;

import com.ltd.controllers.PaginationUtils;
import com.ltd.pojo.Forum;
import com.ltd.repository.ForumRepository;
import java.util.List;
import java.util.Map;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
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
public class ForumRepositoryImpl implements ForumRepository {

    @Autowired
    private LocalSessionFactoryBean factory;

    @Override
    public Forum findById(int forumId) {
        Session session = this.factory.getObject().getCurrentSession();
        return session.get(Forum.class, forumId); // Tìm Forum bằng ID
    }

    @Override
    public List<Forum> getPosts(Map<String, String> params) {
        int page = Integer.parseInt(params.getOrDefault("forumPage", "1"));
        int pageSize = 3; // Số bài đăng mỗi trang

        Session session = this.factory.getObject().getCurrentSession();
        Query query = session.createQuery("FROM Forum", Forum.class);

        // Áp dụng phân trang
        PaginationUtils.applyPagination(query, page, pageSize);

        return query.getResultList();
    }

    @Override
    public long countAllForum() {
        Session s = this.factory.getObject().getCurrentSession();
        CriteriaBuilder b = s.getCriteriaBuilder();
        CriteriaQuery<Long> q = b.createQuery(Long.class);
        Root<Forum> root = q.from(Forum.class);
        q.select(b.count(root));

        return s.createQuery(q).getSingleResult();
    }

    @Override
    public List<Forum> getAllPosts() {
        Session session = this.factory.getObject().getCurrentSession();
        Query query = session.createQuery("FROM Forum", Forum.class);
        return query.getResultList();
    }

    @Override
    public void addForum(Forum forum) {
        Session s = this.factory.getObject().getCurrentSession();
        s.save(forum);
    }

}
