package com.kanbanedchain.lianatasks.DTOs;

import com.kanbanedchain.lianatasks.Models.Board;
import com.kanbanedchain.lianatasks.Models.Role;
import com.kanbanedchain.lianatasks.Models.User;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


public class UserDTO {
    private long id;
    private String name;
    private String username;
    private String email;
    private String password;
    private Set<Role> roles = new HashSet<>();
    private List<Board> boards = new ArrayList<>();

    public UserDTO(User user) {
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public List<Board> getBoard() {
        return boards;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    public void setBoard(List<Board> board) {
        this.boards = board;
    }
}
