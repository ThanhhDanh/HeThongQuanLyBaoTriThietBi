/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ltd.pojo;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Acer
 */
@Entity
@Table(name = "incident")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Incident.findAll", query = "SELECT i FROM Incident i"),
    @NamedQuery(name = "Incident.findById", query = "SELECT i FROM Incident i WHERE i.id = :id"),
    @NamedQuery(name = "Incident.findBySeverityLevel", query = "SELECT i FROM Incident i WHERE i.severityLevel = :severityLevel"),
    @NamedQuery(name = "Incident.findByIncidentTime", query = "SELECT i FROM Incident i WHERE i.incidentTime = :incidentTime"),
    @NamedQuery(name = "Incident.findByIncidentStatus", query = "SELECT i FROM Incident i WHERE i.incidentStatus = :incidentStatus")})
public class Incident implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Lob
    @Size(min = 1, max = 65535)
    @Column(name = "incident_description")
    private String incidentDescription;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "severity_level")
    private String severityLevel;
    @Basic(optional = false)
    @Column(name = "incident_time")
    @Temporal(TemporalType.DATE)
    private Date incidentTime;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "incident_status")
    private String incidentStatus;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "incidentId")
    private Collection<Repairhistory> repairhistoryCollection;
    @JoinColumn(name = "equipment_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Equipment equipmentId;
    @JoinColumn(name = "assigned_user_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private User assignedUserId;
    
    @PrePersist
    protected void onCreate() {
        // Thiết lập ngày hiện tại khi đối tượng được tạo mới
        if (this.incidentTime == null) {
            this.incidentTime = new Date();
        }
    }

    public Incident() {
    }

    public Incident(Integer id) {
        this.id = id;
    }

    public Incident(Integer id, String incidentDescription, String severityLevel, Date incidentTime, String incidentStatus) {
        this.id = id;
        this.incidentDescription = incidentDescription;
        this.severityLevel = severityLevel;
        this.incidentTime = incidentTime;
        this.incidentStatus = incidentStatus;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getIncidentDescription() {
        return incidentDescription;
    }

    public void setIncidentDescription(String incidentDescription) {
        this.incidentDescription = incidentDescription;
    }

    public String getSeverityLevel() {
        return severityLevel;
    }

    public void setSeverityLevel(String severityLevel) {
        this.severityLevel = severityLevel;
    }

    public Date getIncidentTime() {
        return incidentTime;
    }

    public void setIncidentTime(Date incidentTime) {
        this.incidentTime = incidentTime;
    }

    public String getIncidentStatus() {
        return incidentStatus;
    }

    public void setIncidentStatus(String incidentStatus) {
        this.incidentStatus = incidentStatus;
    }

    @XmlTransient
    public Collection<Repairhistory> getRepairhistoryCollection() {
        return repairhistoryCollection;
    }

    public void setRepairhistoryCollection(Collection<Repairhistory> repairhistoryCollection) {
        this.repairhistoryCollection = repairhistoryCollection;
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

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Incident)) {
            return false;
        }
        Incident other = (Incident) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.ltd.pojo.Incident[ id=" + id + " ]";
    }
    
}
