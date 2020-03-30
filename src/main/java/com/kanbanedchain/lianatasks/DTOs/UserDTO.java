package com.kanbanedchain.lianatasks.DTOs;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.kanbanedchain.lianatasks.Models.User;
import org.springframework.util.Assert;

public class UserDTO {

    private String username;

    private String password;

    public UserDTO(User user) {
        Assert.notNull(user.getUsername());
        Assert.notNull(user.getPassword());
        this.username = user.getUsername();
        this.password = user.getPassword();
    }

    public UserDTO(String username, String password) {
        this.username = username;
        this.password = password;
    }

    protected UserDTO() {
        //Default Constructor For Spring
    }

    @JsonProperty
    public String getUsername() {
        return username;
    }

    @JsonProperty
    public String getPassword() {
        return password;
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }

    @Override
    public String toString() {
        return "UserDTO{" +
                "username='" + username + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
