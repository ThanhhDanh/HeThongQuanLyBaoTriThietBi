/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ltd.pojo;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Acer
 */
@Entity
@Table(name = "forum_view")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "ForumView.findAll", query = "SELECT f FROM ForumView f"),
    @NamedQuery(name = "ForumView.findById", query = "SELECT f FROM ForumView f WHERE f.id = :id"),
    @NamedQuery(name = "ForumView.findByViewedDate", query = "SELECT f FROM ForumView f WHERE f.viewedDate = :viewedDate")})
public class ForumView implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Column(name = "viewed_date")
    @Temporal(TemporalType.DATE)
    private Date viewedDate;
    @JoinColumn(name = "forum_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Forum forumId;
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private User userId;
    
    @PrePersist
    protected void onCreate() {
        // Thiết lập ngày hiện tại khi đối tượng được tạo mới
        if (this.viewedDate == null) {
            this.viewedDate = new Date();
        }
    }

    public ForumView() {
    }

    public ForumView(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getViewedDate() {
        return viewedDate;
    }

    public void setViewedDate(Date viewedDate) {
        this.viewedDate = viewedDate;
    }

    public Forum getForumId() {
        return forumId;
    }

    public void setForumId(Forum forumId) {
        this.forumId = forumId;
    }

    public User getUserId() {
        return userId;
    }

    public void setUserId(User userId) {
        this.userId = userId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ForumView)) {
            return false;
        }
        ForumView other = (ForumView) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.ltd.pojo.ForumView[ id=" + id + " ]";
    }
    
}
