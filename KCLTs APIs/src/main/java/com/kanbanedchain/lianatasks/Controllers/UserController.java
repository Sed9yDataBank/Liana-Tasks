package com.kanbanedchain.lianatasks.Controllers;

import com.kanbanedchain.lianatasks.DTOs.UserListDTO;
import com.kanbanedchain.lianatasks.Services.UserService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/users")
@CrossOrigin(origins = "http://localhost:4200")
public class UserController {

    @Autowired
    private UserService service;

    @GetMapping("/getAllUser")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<UserListDTO> getAllUser() {
        UserListDTO userListDTO = new UserListDTO();
        userListDTO.setUserList(service.getAllUser());
        return new ResponseEntity<UserListDTO>(userListDTO, HttpStatus.OK);
    }

    @GetMapping("/getUser/{userId}")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<UserListDTO> getUserById(@PathVariable("userId") UUID userId) {
        UserListDTO userListDTO = new UserListDTO();
        BeanUtils.copyProperties(service.getUser(userId), userListDTO);
        return new ResponseEntity<UserListDTO>(userListDTO, HttpStatus.OK);
    }
}

