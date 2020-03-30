package com.kanbanedchain.lianatasks.DTOs;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.kanbanedchain.lianatasks.Models.Board;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class BoardDTO {

    @JsonIgnore
    private static Logger logger = LoggerFactory.getLogger(BoardDTO.class);

    @JsonIgnore
    private Board board;

    public BoardDTO(Board board) {
        this.board = board;
    }

    @JsonProperty
    public String getTitle() {
        return board.getTitle();
    }

    @JsonProperty
    public String getBackgroundImagePath() {
        return board.getBackgroundImagePath();
    }


}
