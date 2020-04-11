package com.kanbanedchain.lianatasks.Services;

import com.kanbanedchain.lianatasks.DTOs.BoardDTO;
import com.kanbanedchain.lianatasks.DTOs.UserDTO;
import com.kanbanedchain.lianatasks.Models.User;

import java.util.List;
import java.util.UUID;

public interface UserService {

    List<UserDTO> getAllUser();

    User getUser(UUID userId);

    List<UserDTO> getUserByBoardsId(UUID boardId);

    List<BoardDTO> getAllBoardByUser(UUID userId);
}
