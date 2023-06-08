package com.sample.carmarket.entity;

import io.jmix.core.entity.annotation.JmixGeneratedValue;
import io.jmix.core.metamodel.annotation.DependsOnProperties;
import io.jmix.core.metamodel.annotation.InstanceName;
import io.jmix.core.metamodel.annotation.JmixEntity;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.util.Date;
import java.util.UUID;

@JmixEntity
@Table(name = "CAR", indexes = {
        @Index(name = "IDX_CAR_MODEL", columnList = "MODEL_ID")
}, uniqueConstraints = {
        @UniqueConstraint(name = "IDX_CAR_REGNUMB", columnNames = {"REGISTRATION_NUMBER"})
})
@Entity
public class Car {
    @JmixGeneratedValue
    @Column(name = "ID", nullable = false)
    @Id
    private UUID id;


    @Column(name = "REGISTRATION_NUMBER", length = 6)
    private String registrationNumber;

    @JoinColumn(name = "MODEL_ID")
    @ManyToOne(fetch = FetchType.LAZY)
    private Model model;

    @Max(message = "Максимальное значение года: 2030", value = 2030)
    @Min(message = "Минимальное значение года: 1990", value = 1990)
    @Column(name = "PRODUCTION_YEAR")
    private Integer productionYear;

    @Column(name = "STATUS")
    private Integer status;

    @Column(name = "DATE_OF_SALE")
    @Temporal(TemporalType.DATE)
    private Date dateOfSale;

    public void setProductionYear(Integer productionYear) {
        this.productionYear = productionYear;
    }

    public Integer getProductionYear() {
        return productionYear;
    }

    public Date getDateOfSale() {
        return dateOfSale;
    }

    public void setDateOfSale(Date dateOfSale) {
        this.dateOfSale = dateOfSale;
    }

    public Status getStatus() {
        return status == null ? null : Status.fromId(status);
    }

    public void setStatus(Status status) {
        this.status = status == null ? null : status.getId();
    }

    public Model getModel() {
        return model;
    }

    public void setModel(Model model) {
        this.model = model;
    }

    public String getRegistrationNumber() {
        return registrationNumber;
    }

    public void setRegistrationNumber(String registrationNumber) {
        this.registrationNumber = registrationNumber;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    @InstanceName
    @DependsOnProperties({"registrationNumber"})
    public String getInstanceName() {
        return String.format("%s", registrationNumber);
    }
}