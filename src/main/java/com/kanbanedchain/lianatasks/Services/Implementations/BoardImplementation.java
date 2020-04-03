package com.kanbanedchain.lianatasks.Services.Implementations;

import com.kanbanedchain.lianatasks.DTOs.BoardDTO;
import com.kanbanedchain.lianatasks.DTOs.TaskDTO;
import com.kanbanedchain.lianatasks.Models.Board;
import com.kanbanedchain.lianatasks.Models.Task;
import com.kanbanedchain.lianatasks.Repositories.BoardRepository;
import com.kanbanedchain.lianatasks.Repositories.UserRepository;
import com.kanbanedchain.lianatasks.Services.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BoardImplementation implements BoardService {

    @Autowired
    private BoardRepository boardRepository;

    @Autowired
    private UserRepository userRepository;

    @Override
    @Transactional
    public List<Board> getAllBoards() {
        List<Board> boardsList = new ArrayList<>();
        boardRepository.findAll().forEach(boardsList::add);
        return boardsList;
    }

    @Override
    @Transactional
    public Optional<Board> getBoardById(Long id) {
        return boardRepository.findById(id);
    }

    @Override
    @Transactional
    public Optional<Board> getBoardByTitle(String title) {
        return boardRepository.findBoardByTitle(title);
    }

    @Override
    @Transactional
    public Board saveNewBoard(BoardDTO boardDTO, Long id) {

        return userRepository.findById(id).map(user -> {
            boardDTO.setUser(user);
            return boardRepository.save(convertDTOToBoard(boardDTO));
        }).orElseThrow(() -> new SecurityException("User With " + id + " Was Not Found , Unable To Create Board"));
    }

    @Override
    @Transactional
    public Board updateBoard(Board oldBoard, BoardDTO newBoardDTO) {
        oldBoard.setTitle(newBoardDTO.getTitle());
        oldBoard.setBackgroundImagePath((newBoardDTO.getBackgroundImagePath()));
        return boardRepository.save(oldBoard);
    }

    @Override
    @Transactional
    public void deleteBoard(Board board) {
        boardRepository.delete(board);
    }

    @Override
    @Transactional
    public Board addNewTaskToBoard(Long boardId, TaskDTO taskDTO) {
        Board board = boardRepository.findById(boardId).get();
        board.addTask(convertDTOToTask(taskDTO));
        return boardRepository.save(board);
    }

    @Override
    @Transactional
    public List<Board> listAllBoardsByUserId() {
        return boardRepository.listAllBoardsByUserId().collect(Collectors.toCollection(ArrayList::new));
    }

    @Override
    @Transactional
    public List<BoardDTO> getBoardsByUser(Long id) {
        return getBoardById(id)
                .stream()
                .map(board -> new BoardDTO(board))
                .collect(Collectors.toList());
    }

    private Board convertDTOToBoard(BoardDTO boardDTO){
        Board board = new Board();
        board.setTitle(boardDTO.getTitle());
        board.setBackgroundImagePath(boardDTO.getBackgroundImagePath());
        return board;
    }

    private Task convertDTOToTask(TaskDTO taskDTO) {
        Task task = new Task();
        task.setTitle(taskDTO.getTitle());
        task.setStatus(taskDTO.getStatus());
        return task;
    }
}
