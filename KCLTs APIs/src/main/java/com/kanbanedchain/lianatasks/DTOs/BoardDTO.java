package com.kanbanedchain.lianatasks.DTOs;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.kanbanedchain.lianatasks.Models.Board;
import com.kanbanedchain.lianatasks.Models.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Optional;
import java.util.UUID;


public class BoardDTO {

    @JsonIgnore
    private static Logger logger = LoggerFactory.getLogger(BoardDTO.class);

    @JsonIgnore
    private Board board;

    private UUID admin;

    @JsonIgnore
    private List<User> users;

    public BoardDTO(Board board) {
        this.board = board;
    }

    @JsonProperty
    public UUID getBoardId() {
        return board.getBoardId();
    }

    @JsonProperty
    public String getTitle() {
        return board.getTitle();
    }

    @JsonProperty
    public Optional<String> getBackgroundImagePath() {
        return board.getBackgroundImagePath();
    }

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

    public void setBackgroundImagePath() {
    }

    public UUID getAdmin() {
        return admin;
    }

    public void setAdmin(UUID admin) {
        this.admin = admin;
    }
}
