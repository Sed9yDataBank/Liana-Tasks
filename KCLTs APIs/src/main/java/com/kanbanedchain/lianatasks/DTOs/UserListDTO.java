package com.kanbanedchain.lianatasks.DTOs;

import java.util.List;

public class UserListDTO {

    private List<UserDTO> userList;

    public List<UserDTO> getUserList() {
        return userList;
    }

    public void setUserList(List<UserDTO> userList) {
        this.userList = userList;
    }
}

