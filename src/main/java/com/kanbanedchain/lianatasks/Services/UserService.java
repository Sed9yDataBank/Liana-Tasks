package com.kanbanedchain.lianatasks.Services;

import com.kanbanedchain.lianatasks.DTOs.UserDTO;
import com.kanbanedchain.lianatasks.Models.User;

import java.util.List;

public interface UserService {

    public List<UserDTO> getAllUser();

    public List<UserDTO> getUserByBoardsId(Long boardId);

    public User getUser(Long id);
}
