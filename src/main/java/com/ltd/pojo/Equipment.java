/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ltd.pojo;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author Acer
 */
@Entity
@Table(name = "equipment")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Equipment.findAll", query = "SELECT e FROM Equipment e"),
    @NamedQuery(name = "Equipment.findById", query = "SELECT e FROM Equipment e WHERE e.id = :id"),
    @NamedQuery(name = "Equipment.findByName", query = "SELECT e FROM Equipment e WHERE e.name = :name"),
    @NamedQuery(name = "Equipment.findByCode", query = "SELECT e FROM Equipment e WHERE e.code = :code"),
    @NamedQuery(name = "Equipment.findByType", query = "SELECT e FROM Equipment e WHERE e.type = :type"),
    @NamedQuery(name = "Equipment.findByManufacturer", query = "SELECT e FROM Equipment e WHERE e.manufacturer = :manufacturer"),
    @NamedQuery(name = "Equipment.findByPurchaseDate", query = "SELECT e FROM Equipment e WHERE e.purchaseDate = :purchaseDate"),
    @NamedQuery(name = "Equipment.findByCurrentStatus", query = "SELECT e FROM Equipment e WHERE e.currentStatus = :currentStatus")})
public class Equipment implements Serializable {

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "equipmentId")
    @JsonIgnore
    private Collection<Invoicedetail> invoicedetailCollection;

    @JoinColumn(name = "category_id", referencedColumnName = "id")
    @ManyToOne
    @JsonIgnore
    private Category categoryId;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "equipmentId")
    @JsonIgnore
    private Collection<Service> serviceCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "equipmentId")
    @JsonIgnore
    private Collection<Equipmentimage> equipmentimageCollection;

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100, message = "{equipment.name.errorMess}")
    @Column(name = "name")
    private String name;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50, message = "{equipment.code.errorMess}")
    @Column(name = "code")
    private String code;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "type")
    private String type;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100, message = "{equipment.manufacturer.errorMess}")
    @Column(name = "manufacturer")
    private String manufacturer;
    @Basic(optional = false)
    @Column(name = "purchase_date")
    @Temporal(TemporalType.DATE)
    private Date purchaseDate;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50, message = "{equipment.currentStatus.errorMess}")
    @Column(name = "current_status")
    private String currentStatus;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "equipmentId")
    @JsonIgnore
    private Collection<Equipmentlocation> equipmentlocationCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "equipmentId")
    @JsonIgnore
    private Collection<Maintenanceschedule> maintenancescheduleCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "equipmentId")
    @JsonIgnore
    private Collection<Incident> incidentCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "equipmentId")
    @JsonIgnore
    private Collection<Repairhistory> repairhistoryCollection;
    
    
    @PrePersist
    protected void onCreate() {
        // Thiết lập ngày hiện tại khi đối tượng được tạo mới
        if (this.purchaseDate == null) {
            this.purchaseDate = new Date();
        }
    }
    
    
    
    @Transient
    private MultipartFile file;

    public Equipment() {
    }

    public Equipment(Integer id) {
        this.id = id;
    }

    public Equipment(Integer id, String name, String code, String type, String manufacturer, Date purchaseDate, String currentStatus) {
        this.id = id;
        this.name = name;
        this.code = code;
        this.type = type;
        this.manufacturer = manufacturer;
        this.purchaseDate = purchaseDate;
        this.currentStatus = currentStatus;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }

    public Date getPurchaseDate() {
        return purchaseDate;
    }

    public void setPurchaseDate(Date purchaseDate) {
        this.purchaseDate = purchaseDate;
    }

    public String getCurrentStatus() {
        return currentStatus;
    }

    public void setCurrentStatus(String currentStatus) {
        this.currentStatus = currentStatus;
    }

    @XmlTransient
    public Collection<Equipmentlocation> getEquipmentlocationCollection() {
        return equipmentlocationCollection;
    }

    public void setEquipmentlocationCollection(Collection<Equipmentlocation> equipmentlocationCollection) {
        this.equipmentlocationCollection = equipmentlocationCollection;
    }

    @XmlTransient
    public Collection<Maintenanceschedule> getMaintenancescheduleCollection() {
        return maintenancescheduleCollection;
    }

    public void setMaintenancescheduleCollection(Collection<Maintenanceschedule> maintenancescheduleCollection) {
        this.maintenancescheduleCollection = maintenancescheduleCollection;
    }

    @XmlTransient
    public Collection<Incident> getIncidentCollection() {
        return incidentCollection;
    }

    public void setIncidentCollection(Collection<Incident> incidentCollection) {
        this.incidentCollection = incidentCollection;
    }

    @XmlTransient
    public Collection<Repairhistory> getRepairhistoryCollection() {
        return repairhistoryCollection;
    }

    public void setRepairhistoryCollection(Collection<Repairhistory> repairhistoryCollection) {
        this.repairhistoryCollection = repairhistoryCollection;
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
        if (!(object instanceof Equipment)) {
            return false;
        }
        Equipment other = (Equipment) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.ltd.pojo.Equipment[ id=" + id + " ]";
    }

    @XmlTransient
    public Collection<Service> getServiceCollection() {
        return serviceCollection;
    }

    public void setServiceCollection(Collection<Service> serviceCollection) {
        this.serviceCollection = serviceCollection;
    }

    @XmlTransient
    public Collection<Equipmentimage> getEquipmentimageCollection() {
        return equipmentimageCollection;
    }

    public void setEquipmentimageCollection(Collection<Equipmentimage> equipmentimageCollection) {
        this.equipmentimageCollection = equipmentimageCollection;
    }

    public Category getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Category categoryId) {
        this.categoryId = categoryId;
    }

    /**
     * @return the file
     */
    public MultipartFile getFile() {
        return file;
    }

    /**
     * @param file the file to set
     */
    public void setFile(MultipartFile file) {
        this.file = file;
    }

    @XmlTransient
    public Collection<Invoicedetail> getInvoicedetailCollection() {
        return invoicedetailCollection;
    }

    public void setInvoicedetailCollection(Collection<Invoicedetail> invoicedetailCollection) {
        this.invoicedetailCollection = invoicedetailCollection;
    }
    
}
