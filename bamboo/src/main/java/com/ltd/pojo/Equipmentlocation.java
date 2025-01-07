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
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Acer
 */
@Entity
@Table(name = "equipmentlocation")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Equipmentlocation.findAll", query = "SELECT e FROM Equipmentlocation e"),
    @NamedQuery(name = "Equipmentlocation.findById", query = "SELECT e FROM Equipmentlocation e WHERE e.id = :id"),
    @NamedQuery(name = "Equipmentlocation.findByCurrentLocation", query = "SELECT e FROM Equipmentlocation e WHERE e.currentLocation = :currentLocation"),
    @NamedQuery(name = "Equipmentlocation.findByUpdateDate", query = "SELECT e FROM Equipmentlocation e WHERE e.updateDate = :updateDate")})
public class Equipmentlocation implements Serializable {

    @Basic(optional = false)
    @Column(name = "created_date")
    @Temporal(TemporalType.DATE)
    private Date createdDate;

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "current_location")
    private String currentLocation;
    @Basic(optional = false)
    @Column(name = "update_date")
    @Temporal(TemporalType.DATE)
    private Date updateDate;
    @JoinColumn(name = "equipment_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Equipment equipmentId;
    
    @PrePersist
    protected void onCreate() {
        // Thiết lập ngày hiện tại khi đối tượng được tạo mới
        if (this.updateDate == null) {
            this.updateDate = new Date();
        }
        if (this.createdDate == null) {
            this.createdDate = new Date();
        }
    }

    public Equipmentlocation() {
    }

    public Equipmentlocation(Integer id) {
        this.id = id;
    }

    public Equipmentlocation(Integer id, String currentLocation, Date updateDate) {
        this.id = id;
        this.currentLocation = currentLocation;
        this.updateDate = updateDate;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCurrentLocation() {
        return currentLocation;
    }

    public void setCurrentLocation(String currentLocation) {
        this.currentLocation = currentLocation;
    }

    public Date getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }

    public Equipment getEquipmentId() {
        return equipmentId;
    }

    public void setEquipmentId(Equipment equipmentId) {
        this.equipmentId = equipmentId;
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
        if (!(object instanceof Equipmentlocation)) {
            return false;
        }
        Equipmentlocation other = (Equipmentlocation) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.ltd.pojo.Equipmentlocation[ id=" + id + " ]";
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }
    
}
