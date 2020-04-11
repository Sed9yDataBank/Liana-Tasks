package com.kanbanedchain.lianatasks.Models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.springframework.stereotype.Component;

import java.util.UUID;

@Entity
@Component
public class Invitation {

    @Id
    private UUID id;

    private UUID passId;

    private UUID userId;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public UUID getPassId() {
        return passId;
    }

    public static void setPassId(UUID passId) {
        passId = passId;
    }

    public UUID getUserId() {
        return userId;
    }

    public static void setUserId(UUID userId) {
        userId = userId;
    }
}


