package com.kanbanedchain.lianatasks.DTOs;

import java.util.List;

public class BoardListDTO {

    private List<BoardDTO> boardList;

    public List<BoardDTO> getBoardList() {
        return boardList;
    }

    public void setBoardList(List<BoardDTO> boardList) {
        this.boardList = boardList;
    }

}
