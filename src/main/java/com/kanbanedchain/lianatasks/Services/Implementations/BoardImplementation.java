package com.kanbanedchain.lianatasks.Services.Implementations;

import com.amazonaws.services.dynamodbv2.xspec.L;
import com.kanbanedchain.lianatasks.Bucket.BucketName;
import com.kanbanedchain.lianatasks.DTOs.BoardDTO;
import com.kanbanedchain.lianatasks.DTOs.BoardListDTO;
import com.kanbanedchain.lianatasks.DTOs.NewBoardDTO;
import com.kanbanedchain.lianatasks.DTOs.TaskDTO;
import com.kanbanedchain.lianatasks.Exceptions.ApiRequestException;
import com.kanbanedchain.lianatasks.FileStore.FileStore;
import com.kanbanedchain.lianatasks.Models.Board;
import com.kanbanedchain.lianatasks.Models.Invitation;
import com.kanbanedchain.lianatasks.Models.Task;
import com.kanbanedchain.lianatasks.Models.User;
import com.kanbanedchain.lianatasks.Repositories.BoardRepository;
import com.kanbanedchain.lianatasks.Repositories.InvitationRepository;
import com.kanbanedchain.lianatasks.Repositories.PassCodeRepository;
import com.kanbanedchain.lianatasks.Repositories.UserRepository;
import com.kanbanedchain.lianatasks.Services.BoardService;
import com.kanbanedchain.lianatasks.Services.InvitationService;
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

    @Autowired
    private PassCodeRepository passCodeRepository;
    @Autowired
    private InvitationRepository invitationRepository;
    @Autowired
    private InvitationService mailService;

    @Override
    @Transactional
    public List<Board> getAllBoards() {
        return new ArrayList<Board>(boardRepository.findAll());
    }

    @Override
    @Transactional
    public Optional<Board> getBoardById(UUID boardId) {
        return boardRepository.findById(boardId);
    }

    @Override
    @Transactional
    public UUID saveNewBoard(NewBoardDTO newBoardDTO, UUID admin){
        Board board = new Board();
        board.setTitle(newBoardDTO.getTitle());
        board.setAdmin(admin);
        User user = Objects.requireNonNull(userRepository.findById(admin).orElse(null));
        List<User> userList = new ArrayList<User>();
        userList.add(user);
        board.setUsers(userList);
        Board savedBoard = boardRepository.save(board);


        String code = mailService.generatePassCodeForBoard(savedBoard.getBoardId());
        UUID[] users = newBoardDTO.getUsers();
        inviteUsers(users, code, savedBoard.getBoardId());

        return savedBoard.getBoardId();
    }

    @Override
    @Transactional
    public void saveBoardImage(UUID boardId, MultipartFile file) {
        isFileEmpty(file);
        isImage(file);

        BoardDTO board = getBoardOrThrow(boardId);
        Map<String, String> metadata = extractMetadata(file);

        String path = String.format("%s/%s", BucketName.BACKGROUND_IMAGE.getBucketName(), board.getBoardId());
        String filename = String.format("%s-%s", file.getOriginalFilename(), UUID.randomUUID());

        try {
            fileStore.save(path, filename, Optional.of(metadata), file.getInputStream());
            board.setBackgroundImagePath();
        } catch (IOException e) {
            throw new ApiRequestException(board.getBackgroundImagePath() + " Could Not Be Saved");
        }
    }

    @Override
    @Transactional
    public byte[] downloadBoardImage(UUID boardId) {
        BoardDTO board = getBoardOrThrow(boardId);

        String path = String.format("%s/%s",
                BucketName.BACKGROUND_IMAGE.getBucketName(),
                board.getBoardId());

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
    public Board addNewTaskToBoard(UUID boardId, TaskDTO taskDTO) {
        Board board = Objects.requireNonNull(boardRepository.findById(boardId).orElse(null));
        board.addTask(convertDTOToTask(taskDTO));
        return boardRepository.save(board);
    }

    @Override
    @Transactional
    public boolean addUser(UUID userId, String code) {
        boolean invited;
        User user = Objects.requireNonNull(userRepository.findById(userId).orElse(null));
        UUID passId = passCodeRepository.getPassIdByCode(code);
        boolean ifInvited = invitationRepository.existsByUserId(userId);
        List<UUID> invitedPid = invitationRepository.findByUserId(userId);
        boolean checked = false;
        for(UUID id: invitedPid) {
            if(id.equals(passId)) {
                checked = true;
                break;
            }
        }

        if( ifInvited && checked) {
            invited = true;
            Board p = Objects.requireNonNull(boardRepository.findById(passId).orElse(null));
            List<User> users = p.getUsers();
            users.add(user);
            p.setUsers(users);
            boardRepository.save(p);
        } else {
            invited = false;
        }
        return invited;
    }

    @Override
    @Transactional
    public void inviteUsers(UUID[] users, String code, UUID passId) {

        for( UUID userId: users) {
            String email = Objects.requireNonNull(userRepository.findById(userId).orElse(null)).getEmail();
            mailService.sendPassCode(email, code,
                    Objects.requireNonNull(boardRepository.findById(passId).orElse(null)));
            Invitation invitation = new Invitation();
            Invitation.setPassId(passId);
            Invitation.setUserId(userId);
            invitationRepository.save(invitation);
        }
    }

    @Override
    @Transactional
    public BoardListDTO getAllByAdmin(UUID id) {
        BoardListDTO list = new BoardListDTO();
        list.setBoardList( (List<BoardDTO>) boardRepository.findByAdmin(id).stream().map
                (BoardDTO::new).collect(Collectors.toList()));
        return list;
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

    private BoardDTO getBoardOrThrow(UUID boardId) {
        BoardListDTO boardListDTO = new BoardListDTO();
        return boardListDTO
                .getBoardList()
                .stream()
                .filter(board -> board.getBoardId().equals(boardId))
                .findFirst()
                .orElseThrow(() -> new IllegalStateException(String.format("Board %s Not Found With Id : ", boardId)));
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
