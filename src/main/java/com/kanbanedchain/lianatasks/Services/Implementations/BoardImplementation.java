package com.kanbanedchain.lianatasks.Services.Implementations;

import com.kanbanedchain.lianatasks.DTOs.BoardDTO;
import com.kanbanedchain.lianatasks.DTOs.TaskDTO;
import com.kanbanedchain.lianatasks.Models.Board;
import com.kanbanedchain.lianatasks.Models.Task;
import com.kanbanedchain.lianatasks.Repositories.BoardRepository;
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
    public Board saveNewBoard(BoardDTO boardDTO) {
        return boardRepository.save(convertDTOToBoard(boardDTO));
    }

    @Override
    @Transactional
    public Board updateBoard(Board oldboard, BoardDTO newBoardDTO) {
        oldboard.setTitle(newBoardDTO.getTitle());
        oldboard.setBackgroundImagePath((newBoardDTO.getBackgroundImagePath()));
        return boardRepository.save(oldboard);
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
