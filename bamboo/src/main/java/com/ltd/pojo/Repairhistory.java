/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ltd.pojo;

import java.io.Serializable;
import java.math.BigDecimal;
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
@Table(name = "repairhistory")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Repairhistory.findAll", query = "SELECT r FROM Repairhistory r"),
    @NamedQuery(name = "Repairhistory.findById", query = "SELECT r FROM Repairhistory r WHERE r.id = :id"),
    @NamedQuery(name = "Repairhistory.findByRepairDate", query = "SELECT r FROM Repairhistory r WHERE r.repairDate = :repairDate"),
    @NamedQuery(name = "Repairhistory.findByRepairType", query = "SELECT r FROM Repairhistory r WHERE r.repairType = :repairType"),
    @NamedQuery(name = "Repairhistory.findByRepairCost", query = "SELECT r FROM Repairhistory r WHERE r.repairCost = :repairCost")})
public class Repairhistory implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @Column(name = "repair_date")
    @Temporal(TemporalType.DATE)
    private Date repairDate;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "repair_type")
    private String repairType;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Basic(optional = false)
    @NotNull
    @Column(name = "repair_cost")
    private BigDecimal repairCost;
    @JoinColumn(name = "equipment_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Equipment equipmentId;
    @JoinColumn(name = "incident_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Incident incidentId;
    @JoinColumn(name = "assigned_user_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private User assignedUserId;
    @JoinColumn(name = "performed_by_user_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private User performedByUserId;
    
    @PrePersist
    protected void onCreate() {
        // Thiết lập ngày hiện tại khi đối tượng được tạo mới
        if (this.repairDate == null) {
            this.repairDate = new Date();
        }
    }

    public Repairhistory() {
    }

    public Repairhistory(Integer id) {
        this.id = id;
    }

    public Repairhistory(Integer id, Date repairDate, String repairType, BigDecimal repairCost) {
        this.id = id;
        this.repairDate = repairDate;
        this.repairType = repairType;
        this.repairCost = repairCost;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getRepairDate() {
        return repairDate;
    }

    public void setRepairDate(Date repairDate) {
        this.repairDate = repairDate;
    }

    public String getRepairType() {
        return repairType;
    }

    public void setRepairType(String repairType) {
        this.repairType = repairType;
    }

    public BigDecimal getRepairCost() {
        return repairCost;
    }

    public void setRepairCost(BigDecimal repairCost) {
        this.repairCost = repairCost;
    }

    public Equipment getEquipmentId() {
        return equipmentId;
    }

    public void setEquipmentId(Equipment equipmentId) {
        this.equipmentId = equipmentId;
    }

    public Incident getIncidentId() {
        return incidentId;
    }

    public void setIncidentId(Incident incidentId) {
        this.incidentId = incidentId;
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
        if (!(object instanceof Repairhistory)) {
            return false;
        }
        Repairhistory other = (Repairhistory) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.ltd.pojo.Repairhistory[ id=" + id + " ]";
    }
    
}
