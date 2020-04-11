package com.kanbanedchain.lianatasks.Models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.springframework.stereotype.Component;

import java.util.UUID;

@Entity
@Component
public class PassCode {

    @Id
    private UUID id;

    private UUID passId;

    private String code;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public UUID getPassId() {
        return passId;
    }

    public void setPassId(UUID passId) {
        this.passId = passId;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
