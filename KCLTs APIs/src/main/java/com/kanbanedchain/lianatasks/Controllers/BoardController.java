package com.kanbanedchain.lianatasks.Controllers;

import com.kanbanedchain.lianatasks.DTOs.*;
import com.kanbanedchain.lianatasks.Models.Board;
import com.kanbanedchain.lianatasks.Models.PassCode;
import com.kanbanedchain.lianatasks.Services.BoardService;
import com.kanbanedchain.lianatasks.Services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/boards")
@CrossOrigin(origins = "http://localhost:4200")
public class BoardController {

    @Autowired
    private BoardService boardService;

    @Autowired
    private UserService userService;
    private UUID boardId;

    @PostMapping("/createBoard/{userId}")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<Boolean> createBoard(@PathVariable(value = "userId") UUID userId,
                                               @Valid @RequestBody NewBoardDTO newBoardDTO) {
        boardService.saveNewBoard(newBoardDTO, userId);
        return new ResponseEntity<Boolean>(true, HttpStatus.OK);
    }

    @PostMapping(
              path = "createBoard/upload/{boardId}",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
        )
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public void setBoardBackgroundImage(@PathVariable(value = "boardId") UUID boardId,
                                       @RequestParam("file") MultipartFile file) {
        boardService.saveBoardImage(boardId, file);
    }

    @GetMapping("/download/{boardId}")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public byte[] downloadBoardImage(@PathVariable("boardId") UUID boardId) {
        return boardService.downloadBoardImage(boardId);
    }

    @GetMapping("/getAllBoards")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<BoardDTO> getAllBoards() {
        return new ResponseEntity<BoardDTO>((BoardDTO) boardService.getAllBoards(), HttpStatus.OK);
    }

    @PutMapping("/updateBoard/{boardId}")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<?> updateBoardById(@PathVariable(value = "boardId") UUID boardId,
                                             @RequestBody BoardDTO boardDTO) {
        try {
            Optional<Board> optionalBoard = boardService.getBoardById(boardId);
            if (optionalBoard.isPresent()) {
                return new ResponseEntity<>(
                        boardService.updateBoard(optionalBoard.get(), boardDTO),
                        HttpStatus.OK);
            } else {
                return noBoardFoundResponse(boardId);
            }
        } catch (Exception e) {
            return errorResponse();
        }
    }

    @PostMapping("/{boardId}/tasks/")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<?> createTaskAssignedToBoard(@PathVariable(value = "boardId") UUID boardId,
                                                       @RequestBody TaskDTO taskDTO){
        try {
            return new ResponseEntity<>(
                    boardService.addNewTaskToBoard(boardId, taskDTO),
                    HttpStatus.CREATED);
        } catch (Exception e) {
            return errorResponse();
        }
    }

    @PostMapping("/inviteUser")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<Boolean> boardInvitation(@RequestBody CodeDTO codeDTO) {
        boardService.addUser(codeDTO.getUserId(), codeDTO.getCode());
        return new ResponseEntity<Boolean>(true,HttpStatus.OK);
    }

    @DeleteMapping("/deleteBoard/{boardId}")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<?> deleteBoardById(@PathVariable(value = "boardId") UUID boardId) {
        return boardService.getBoardById(boardId).map(board -> {
            boardService.deleteBoard(board);
            return ResponseEntity.ok().build();
        }).orElseThrow((

        ) -> new SecurityException("Board With " + boardId + " Was Not Found , Unable To Delete Board"));
    }

    private ResponseEntity<String> errorResponse() {
        return new ResponseEntity<>("Something Went Wrong :( ", HttpStatus.INTERNAL_SERVER_ERROR);
    }
    private ResponseEntity<String> noBoardFoundResponse(UUID boardId) {
        return new ResponseEntity<>("No Board Found With Id: " + boardId, HttpStatus.NOT_FOUND);
    }
}
