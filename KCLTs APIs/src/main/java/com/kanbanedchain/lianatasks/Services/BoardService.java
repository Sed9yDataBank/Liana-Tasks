package com.kanbanedchain.lianatasks.Services;

import com.kanbanedchain.lianatasks.DTOs.BoardDTO;
import com.kanbanedchain.lianatasks.DTOs.BoardListDTO;
import com.kanbanedchain.lianatasks.DTOs.NewBoardDTO;
import com.kanbanedchain.lianatasks.DTOs.TaskDTO;
import com.kanbanedchain.lianatasks.Models.Board;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface BoardService {

    List<Board> getAllBoards();

    Optional<Board> getBoardById(UUID boardId);

    UUID saveNewBoard(NewBoardDTO newBoardDTO, UUID admin);

    void saveBoardImage(UUID boardId, MultipartFile file);

    byte[] downloadBoardImage(UUID boardId);

    Board updateBoard(Board oldBoard, BoardDTO newBoardDTO);

    void deleteBoard(Board Board);

    Board addNewTaskToBoard(UUID boardId, TaskDTO taskDTO);

    boolean addUser(UUID userId, String code);

    void inviteUsers(UUID[] users, String code, UUID passId);

    BoardListDTO getAllByAdmin(UUID id);
}
