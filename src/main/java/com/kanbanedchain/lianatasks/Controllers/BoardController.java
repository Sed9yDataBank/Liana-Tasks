package com.kanbanedchain.lianatasks.Controllers;

import com.kanbanedchain.lianatasks.DTOs.BoardDTO;
import com.kanbanedchain.lianatasks.DTOs.BoardListDTO;
import com.kanbanedchain.lianatasks.DTOs.TaskDTO;
import com.kanbanedchain.lianatasks.Models.Board;
import com.kanbanedchain.lianatasks.Services.BoardService;
import com.kanbanedchain.lianatasks.Services.UserService;
import com.kanbanedchain.lianatasks.Utils.Client;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.util.Optional;

@RestController
@RequestMapping("/boards")
@CrossOrigin(origins = "http://localhost:4200")
public class BoardController {
    final String clientUrl = Client.clientUrl;

    @Autowired
    private BoardService boardService;

    @Autowired
    private UserService userService;

    @PostMapping("/createBoard/{id}")
    @CrossOrigin(origins = clientUrl)
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<Boolean> createBoard(@PathVariable(value = "id") Long id,
                                               @Valid @RequestBody BoardDTO newBoardDto) {
        boardService.saveNewBoard(newBoardDto, id);
        return new ResponseEntity<Boolean>(true, HttpStatus.OK);
    }

    @PostMapping(
              path = "createBoard/upload/{id}",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
        )
    @CrossOrigin(origins = clientUrl)
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
        public void setBoardBackgroundImage(@PathVariable(value = "board_Id") Long id,
                                       @RequestParam("file") MultipartFile file) {
        boardService.saveBoardImage(id, file);
    }

    @GetMapping("/download/{id}")
    @CrossOrigin(origins = clientUrl)
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public byte[] downloadBoardImage(@PathVariable("board_Id") Long id) {
        return boardService.downloadBoardImage(id);
    }

    @GetMapping("/getAllBoards")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    @CrossOrigin(origins = clientUrl)
    public ResponseEntity<BoardDTO> getAllBoards() {
        return new ResponseEntity<BoardDTO>((BoardDTO) boardService.getAllBoards(), HttpStatus.OK);
    }

    @GetMapping("/getAllBoards/{id}")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    @CrossOrigin(origins = clientUrl)
    public ResponseEntity<BoardListDTO> getAllBoardsByUserId(@PathVariable(value = "id") Long id) {

        BoardListDTO boardListDTO = new BoardListDTO();
        boardListDTO.setBoardList(boardService.getBoardsByUser(id));
        return new ResponseEntity<BoardListDTO>(boardListDTO, HttpStatus.OK);
    }

    @PutMapping("/updateBoard/{id}")
    @CrossOrigin(origins = clientUrl)
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<?> updateBoardById(@PathVariable(value = "board_Id") Long id,
                                             @RequestBody BoardDTO boardDTO) {
        try {
            Optional<Board> optionalBoard = boardService.getBoardById(id);
            if (optionalBoard.isPresent()) {
                return new ResponseEntity<>(
                        boardService.updateBoard(optionalBoard.get(), boardDTO),
                        HttpStatus.OK);
            } else {
                return noBoardFoundResponse(id);
            }
        } catch (Exception e) {
            return errorResponse();
        }
    }

    @PostMapping("/{id}/tasks/")
    @CrossOrigin(origins = clientUrl)
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<?> createTaskAssignedToBoard(@PathVariable(value = "board_Id") Long id,
                                                       @RequestBody TaskDTO taskDTO){
        try {
            return new ResponseEntity<>(
                    boardService.addNewTaskToBoard(id, taskDTO),
                    HttpStatus.CREATED);
        } catch (Exception e) {
            return errorResponse();
        }
    }

    @DeleteMapping("/deleteBoard/{id}")
    @CrossOrigin(origins = clientUrl)
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<?> deleteBoardById(@PathVariable(value = "board_Id") Long id) {
        return boardService.getBoardById(id).map(board -> {
            boardService.deleteBoard(board);
            return ResponseEntity.ok().build();
        }).orElseThrow(() -> new SecurityException("Board With " + id + " Was Not Found , Unable To Delete Board"));
    }

    @GetMapping("")
    @CrossOrigin(origins = clientUrl)
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<?> getBoardByTitle(@RequestParam String title) {
        try {
            Optional<Board> optionalBoard = boardService.getBoardByTitle(title);
            if (optionalBoard.isPresent()) {
                return new ResponseEntity<>(
                        optionalBoard.get(),
                        HttpStatus.OK);
            } else {
                return new ResponseEntity<>("No Board Found With A Title: " + title, HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            return errorResponse();
        }
    }

    //Quick Exceptions Handlers
    private ResponseEntity<String> errorResponse(){
        return new ResponseEntity<>("Something Went Wrong :( ", HttpStatus.INTERNAL_SERVER_ERROR);
    }
    private ResponseEntity<String> noBoardFoundResponse(Long id){
        return new ResponseEntity<>("No Board Found With Id: " + id, HttpStatus.NOT_FOUND);
    }
}
