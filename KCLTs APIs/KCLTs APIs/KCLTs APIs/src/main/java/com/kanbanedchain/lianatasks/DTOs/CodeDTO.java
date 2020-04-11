package com.kanbanedchain.lianatasks.DTOs;

import java.util.UUID;

public class CodeDTO {

    private String code;
    private UUID userId;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public UUID getUserId() {
        return userId;
    }

    public void setUserId(UUID userId) {
        this.userId = userId;
    }
}
