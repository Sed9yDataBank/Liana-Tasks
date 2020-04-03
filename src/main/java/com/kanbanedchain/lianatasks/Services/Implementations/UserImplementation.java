package com.kanbanedchain.lianatasks.Services.Implementations;

import java.util.List;
import java.util.stream.Collectors;

import com.kanbanedchain.lianatasks.DTOs.UserDTO;
import com.kanbanedchain.lianatasks.Models.User;
import com.kanbanedchain.lianatasks.Repositories.BoardRepository;
import com.kanbanedchain.lianatasks.Repositories.UserRepository;
import com.kanbanedchain.lianatasks.Services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class UserImplementation implements UserService {
    @Autowired
    private BoardRepository boardRepository;
    @Autowired
    private UserRepository userRepository;

    @Override
    @Transactional
    public List<UserDTO> getAllUser() {
        return (List<UserDTO>) userRepository.findAll()
                .stream()
                .map(user -> new UserDTO(user))
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public List<UserDTO> getUserByBoardsId(Long boardId) {
        return boardRepository.findUserByBoardsId(boardId)
                .stream()
                .map(user -> new UserDTO(user))
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public User getUser(Long id) {
        return userRepository.findById(id).get();
    }
}

