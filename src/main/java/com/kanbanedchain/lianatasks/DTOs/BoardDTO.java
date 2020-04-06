package com.kanbanedchain.lianatasks.DTOs;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.kanbanedchain.lianatasks.Models.Board;
import com.kanbanedchain.lianatasks.Models.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Optional;


public class BoardDTO {

    @JsonIgnore
    private static Logger logger = LoggerFactory.getLogger(BoardDTO.class);

    @JsonIgnore
    private Board board;

    @JsonIgnore
    private User user;

    public BoardDTO(Board board) {
        this.board = board;
    }

    @JsonProperty
    public Long getId() {
        return board.getId();
    }

    @JsonProperty
    public String getTitle() {
        return board.getTitle();
    }

    @JsonProperty
    public Optional<String> getBackgroundImagePath() {
        return board.getBackgroundImagePath();
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setBackgroundImagePath() {
    }
}
