package com.kanbanedchain.lianatasks.Controllers;

import com.kanbanedchain.lianatasks.DTOs.BoardListDTO;
import com.kanbanedchain.lianatasks.DTOs.UserListDTO;
import com.kanbanedchain.lianatasks.Models.SubTask;
import com.kanbanedchain.lianatasks.Models.Task;
import com.kanbanedchain.lianatasks.Models.TaskStatus;
import com.kanbanedchain.lianatasks.Services.BoardService;
import com.kanbanedchain.lianatasks.Services.SubTaskService;
import com.kanbanedchain.lianatasks.Services.TaskService;
import com.kanbanedchain.lianatasks.Services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Controller
@RequestMapping("/devs/query/api")
public class DeveloperQueriesController {

    @Autowired
    private SubTaskService subTaskService;

    @Autowired
    private TaskService taskService;

    @Autowired
    private UserService service;

    @Autowired
    private BoardService boardService;

    @GetMapping("/{deadline}")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<?> getSubTaskByDeadline(@PathVariable("deadline") LocalDateTime deadline) {
        try {
            Optional<SubTask> optSubTask = subTaskService.getSubTaskByDeadline(deadline);
            if (optSubTask.isPresent()) {
                return new ResponseEntity<>(
                        optSubTask.get(),
                        HttpStatus.OK);
            } else {
                return new ResponseEntity<>("No SubTask Found With A Deadline: " + deadline,
                        HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            return errorResponse();
        }
    }

    @GetMapping("/{title}")
    public ResponseEntity<?> getTaskByTitle(@PathVariable("title") String title) {
        try {
            Optional<Task> optTask = taskService.getTaskByTitle(title);
            if (optTask.isPresent()) {
                return new ResponseEntity<>(
                        optTask.get(),
                        HttpStatus.OK);
            } else {
                return new ResponseEntity<>("No Task Found With A Title: " + title, HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            return errorResponse();
        }
    }

    @GetMapping("/{status}")
    public ResponseEntity<?> getSubTaskByStatus(@PathVariable("status") TaskStatus status) {
        try {
            Optional<SubTask> optSubTask = subTaskService.getSubTaskByStatus(status);
            if (optSubTask.isPresent()) {
                return new ResponseEntity<>(
                        optSubTask.get(),
                        HttpStatus.OK);
            } else {
                return new ResponseEntity<>("No SubTask Found With A Status: " + status, HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            return errorResponse();
        }
    }

    @GetMapping("/getAllUser/{boardId}")
    public ResponseEntity<UserListDTO> getAllUserByBoardId(@PathVariable("boardId") UUID boardId) {
        UserListDTO userListDTO = new UserListDTO();
        userListDTO.setUserList(service.getUserByBoardsId(boardId));
        return new ResponseEntity<UserListDTO>(userListDTO, HttpStatus.OK);
    }

    private ResponseEntity<String> errorResponse(){
        return new ResponseEntity<>("Something Went Wrong Ops :(", HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @GetMapping("/getAllBoardByAdmin/{id}")
    public ResponseEntity<BoardListDTO> getAllBoardByAdmin(@PathVariable("id") UUID id) {
        BoardListDTO dto = boardService.getAllByAdmin(id);
        return new ResponseEntity<BoardListDTO>(dto, HttpStatus.OK);
    }

    @GetMapping("/getAllBoardByUser/{userId}")
    public ResponseEntity<BoardListDTO> getAllBoardByUser(@PathVariable("userId") UUID userId) {

        BoardListDTO dto = new BoardListDTO();
        dto.setBoardList(service.getAllBoardByUser(userId));
        return new ResponseEntity<BoardListDTO>(dto, HttpStatus.OK);
    }
}
