package com.kanbanedchain.lianatasks.Services.Implementations;

import com.kanbanedchain.lianatasks.Bucket.BucketName;
import com.kanbanedchain.lianatasks.DTOs.BoardDTO;
import com.kanbanedchain.lianatasks.DTOs.BoardListDTO;
import com.kanbanedchain.lianatasks.DTOs.TaskDTO;
import com.kanbanedchain.lianatasks.FileStore.FileStore;
import com.kanbanedchain.lianatasks.Models.Board;
import com.kanbanedchain.lianatasks.Models.Task;
import com.kanbanedchain.lianatasks.Repositories.BoardRepository;
import com.kanbanedchain.lianatasks.Repositories.UserRepository;
import com.kanbanedchain.lianatasks.Services.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

import static org.apache.http.entity.ContentType.*;

@Service
@RequiredArgsConstructor
public class BoardImplementation implements BoardService {

    @Autowired
    private BoardRepository boardRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private FileStore fileStore;

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
    public void saveBoardImage(Long id, MultipartFile file) {
        isFileEmpty(file);
        isImage(file);

        BoardDTO board = getBoardOrThrow(id);
        Map<String, String> metadata = extractMetadata(file);

        String path = String.format("%s/%s", BucketName.BACKGROUND_IMAGE.getBucketName(), board.getId());
        String filename = String.format("%s-%s", file.getOriginalFilename(), UUID.randomUUID());

        try {
            fileStore.save(path, filename, Optional.of(metadata), file.getInputStream());
            board.setBackgroundImagePath();
            boardRepository.save(convertDTOToBoard(board));
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
    }

    @Override
    @Transactional
    public byte[] downloadBoardImage(Long id) {
        BoardDTO board = getBoardOrThrow(id);

        String path = String.format("%s/%s",
                BucketName.BACKGROUND_IMAGE.getBucketName(),
                board.getId());

        return board.getBackgroundImagePath()
                .map(key -> fileStore.download(path, key))
                .orElse(new byte[0]);
    }

    @Override
    @Transactional
    public Board updateBoard(Board oldBoard, BoardDTO newBoardDTO) {
        oldBoard.setTitle(newBoardDTO.getTitle());
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
        board.setBackgroundImagePath(boardDTO.getBackgroundImagePath().toString());
        return board;
    }

    private Task convertDTOToTask(TaskDTO taskDTO) {
        Task task = new Task();
        task.setTitle(taskDTO.getTitle());
        task.setStatus(taskDTO.getStatus());
        return task;
    }

    private Map<String, String> extractMetadata(MultipartFile file) {
        Map<String, String> metadata = new HashMap<>();
        metadata.put("Content-Type", file.getContentType());
        metadata.put("Content-Length", String.valueOf(file.getSize()));
        return metadata;
    }

    private BoardDTO getBoardOrThrow(Long id) {
        BoardListDTO boardListDTO = new BoardListDTO();
        return boardListDTO
                .getBoardList()
                .stream()
                .filter(board -> board.getId().equals(id))
                .findFirst()
                .orElseThrow(() -> new IllegalStateException(String.format("Board %s Not Found With Id : ", id)));
    }

    private void isImage(MultipartFile file) {
        if (!Arrays.asList(
                IMAGE_JPEG.getMimeType(),
                IMAGE_PNG.getMimeType(),
                IMAGE_GIF.getMimeType()).contains(file.getContentType())) {
            throw new IllegalStateException("File Must Be An Image [" + file.getContentType() + "]");
        }
    }

    private void isFileEmpty(MultipartFile file) {
        if (file.isEmpty()) {
            throw new IllegalStateException("Cannot Upload Empty File [ " + file.getSize() + "]");
        }
    }
}
