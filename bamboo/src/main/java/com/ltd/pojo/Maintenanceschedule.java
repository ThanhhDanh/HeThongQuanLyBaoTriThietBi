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
import org.springframework.format.annotation.DateTimeFormat;

/**
 *
 * @author Acer
 */
@Entity
@Table(name = "maintenanceschedule")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Maintenanceschedule.findAll", query = "SELECT m FROM Maintenanceschedule m"),
    @NamedQuery(name = "Maintenanceschedule.findById", query = "SELECT m FROM Maintenanceschedule m WHERE m.id = :id"),
    @NamedQuery(name = "Maintenanceschedule.findByMaintenanceDate", query = "SELECT m FROM Maintenanceschedule m WHERE m.maintenanceDate = :maintenanceDate"),
    @NamedQuery(name = "Maintenanceschedule.findByMaintenanceFrequency", query = "SELECT m FROM Maintenanceschedule m WHERE m.maintenanceFrequency = :maintenanceFrequency"),
    @NamedQuery(name = "Maintenanceschedule.findByMaintenanceType", query = "SELECT m FROM Maintenanceschedule m WHERE m.maintenanceType = :maintenanceType")})
public class Maintenanceschedule implements Serializable {

    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "maintenance_status")
    private String maintenanceStatus;

    @Basic(optional = false)
    @Column(name = "next_maintenance_date")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Temporal(TemporalType.DATE)
    private Date nextMaintenanceDate;
    @Basic(optional = false)
    @Column(name = "reminder_date")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Temporal(TemporalType.DATE)
    private Date reminderDate;

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @Column(name = "maintenance_date")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Temporal(TemporalType.DATE)
    private Date maintenanceDate;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "maintenance_frequency")
    private String maintenanceFrequency;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "maintenance_type")
    private String maintenanceType;
    @JoinColumn(name = "equipment_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Equipment equipmentId;
    @JoinColumn(name = "assigned_user_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private User assignedUserId;
    @JoinColumn(name = "performed_by_user_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private User performedByUserId;

    @PrePersist
    protected void onCreate() {
        // Thiết lập ngày hiện tại khi đối tượng được tạo mới
        if (this.maintenanceDate == null) {
            this.maintenanceDate = new Date();
        }
        if (this.nextMaintenanceDate == null) {
            this.nextMaintenanceDate = new Date();
        }
        if (this.reminderDate == null) {
            this.reminderDate = new Date();
        }
    }


    public Maintenanceschedule() {
    }

    public Maintenanceschedule(Integer id) {
        this.id = id;
    }

    public Maintenanceschedule(Integer id, Date maintenanceDate, String maintenanceFrequency, String maintenanceType) {
        this.id = id;
        this.maintenanceDate = maintenanceDate;
        this.maintenanceFrequency = maintenanceFrequency;
        this.maintenanceType = maintenanceType;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getMaintenanceDate() {
        return maintenanceDate;
    }

    public void setMaintenanceDate(Date maintenanceDate) {
        this.maintenanceDate = maintenanceDate;
    }

    public String getMaintenanceFrequency() {
        return maintenanceFrequency;
    }

    public void setMaintenanceFrequency(String maintenanceFrequency) {
        this.maintenanceFrequency = maintenanceFrequency;
    }

    public String getMaintenanceType() {
        return maintenanceType;
    }

    public void setMaintenanceType(String maintenanceType) {
        this.maintenanceType = maintenanceType;
    }

    public Equipment getEquipmentId() {
        return equipmentId;
    }

    public void setEquipmentId(Equipment equipmentId) {
        this.equipmentId = equipmentId;
    }

    public User getAssignedUserId() {
        return assignedUserId;
    }

    public void setAssignedUserId(User assignedUserId) {
        this.assignedUserId = assignedUserId;
    }

    public User getPerformedByUserId() {
        return performedByUserId;
    }

    public void setPerformedByUserId(User performedByUserId) {
        this.performedByUserId = performedByUserId;
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
        if (!(object instanceof Maintenanceschedule)) {
            return false;
        }
        Maintenanceschedule other = (Maintenanceschedule) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.ltd.pojo.Maintenanceschedule[ id=" + id + " ]";
    }

    public Date getNextMaintenanceDate() {
        return nextMaintenanceDate;
    }

    public void setNextMaintenanceDate(Date nextMaintenanceDate) {
        this.nextMaintenanceDate = nextMaintenanceDate;
    }

    public Date getReminderDate() {
        return reminderDate;
    }

    public void setReminderDate(Date reminderDate) {
        this.reminderDate = reminderDate;
    }

    public String getMaintenanceStatus() {
        return maintenanceStatus;
    }

    public void setMaintenanceStatus(String maintenanceStatus) {
        this.maintenanceStatus = maintenanceStatus;
    }

}
