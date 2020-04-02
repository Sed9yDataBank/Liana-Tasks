package com.kanbanedchain.lianatasks.Models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
@Data
public abstract class AuditModel implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;

    @CreatedDate
    private Date createdDate;

    @LastModifiedDate
    private Date updatedDate;

    public AuditModel(Long id, Date createdDate, Date updatedDate) {
        Id = id;
        this.createdDate = createdDate;
        this.updatedDate = updatedDate;
    }

    protected AuditModel() {
    }

    public AuditModel(Date createdDate, Date updatedDate) {
        this.createdDate = createdDate;
        this.updatedDate = updatedDate;
    }

    public Long getId() {
        return Id;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public Date getUpdatedDate() {
        return updatedDate;
    }
}
