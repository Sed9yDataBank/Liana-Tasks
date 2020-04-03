package com.kanbanedchain.lianatasks.Services;

import com.kanbanedchain.lianatasks.DTOs.BoardDTO;
import com.kanbanedchain.lianatasks.DTOs.TaskDTO;
import com.kanbanedchain.lianatasks.Models.Board;

import java.util.List;
import java.util.Optional;

public interface BoardService {

    List<Board> getAllBoards();

    Optional<Board> getBoardById(Long id);

    Optional<Board> getBoardByTitle(String title);

    Board saveNewBoard(BoardDTO BoardDTO, Long id);

    Board updateBoard(Board oldBoard, BoardDTO newBoardDTO);

    void deleteBoard(Board Board);

    Board addNewTaskToBoard(Long BoardId, TaskDTO taskDTO);

    List<Board> listAllBoardsByUserId();

    List<BoardDTO> getBoardsByUser(Long id);
}
