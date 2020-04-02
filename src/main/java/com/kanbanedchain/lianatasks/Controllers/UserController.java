package com.kanbanedchain.lianatasks.Controllers;

import com.kanbanedchain.lianatasks.DTOs.UserListDTO;
import com.kanbanedchain.lianatasks.Services.UserService;
import com.kanbanedchain.lianatasks.Utils.Client;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

    @Autowired
    private UserService service;

    final String clientUrl = Client.clientUrl;

    @GetMapping("/getAllUser")
    @CrossOrigin(origins = clientUrl)
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<UserListDTO> getAllUser() {
        UserListDTO userListDTO = new UserListDTO();
        userListDTO.setUserList(service.getAllUser());
        return new ResponseEntity<UserListDTO>(userListDTO, HttpStatus.OK);
    }

    @GetMapping("/getAllUser/{id}")
    @CrossOrigin(origins = clientUrl)
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<UserListDTO> getAllUser(@PathVariable("pid") Long id) {
        UserListDTO userListDTO = new UserListDTO();
        userListDTO.setUserList(service.getUserByBoardsId(id));
        return new ResponseEntity<UserListDTO>(userListDTO, HttpStatus.OK);
    }

    @GetMapping("/getUser/{uid}")
    @CrossOrigin(origins = clientUrl)
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<UserListDTO> getUserById(@PathVariable("uid") Long uid) {
        UserListDTO userListDTO = new UserListDTO();
        BeanUtils.copyProperties(service.getUser(uid), userListDTO);
        return new ResponseEntity<UserListDTO>(userListDTO, HttpStatus.OK);
    }
}

