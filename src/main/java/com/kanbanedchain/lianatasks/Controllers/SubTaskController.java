package com.kanbanedchain.lianatasks.Controllers;

import com.kanbanedchain.lianatasks.DTOs.SubTaskDTO;
import com.kanbanedchain.lianatasks.DTOs.TaskDTO;
import com.kanbanedchain.lianatasks.Models.SubTask;
import com.kanbanedchain.lianatasks.Models.Task;
import com.kanbanedchain.lianatasks.Models.TaskStatus;
import com.kanbanedchain.lianatasks.Services.SubTaskService;
import com.kanbanedchain.lianatasks.Services.TaskService;
import com.kanbanedchain.lianatasks.Utils.Client;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Optional;

@RestController
@RequestMapping("/subTasks")
@CrossOrigin(origins = "http://localhost:4200")
public class SubTaskController {
    final String clientUrl = Client.clientUrl;

    @Autowired
    private SubTaskService subTaskService;

    @GetMapping("/All")
    @CrossOrigin(origins = clientUrl)
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<?> getAllSubTasks() {
        try {
            return new ResponseEntity<>(
                    subTaskService.getAllSubTasks(),
                    HttpStatus.OK);
        } catch (Exception e) {
            return errorResponse();
        }
    }

    @GetMapping("/{id}")
    @CrossOrigin(origins = clientUrl)
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<?> getSubTask(@PathVariable Long id) {
        try {
            Optional<SubTask> optSubTask = subTaskService.getSubTaskById(id);
            if (optSubTask.isPresent()) {
                return new ResponseEntity<>(
                        optSubTask.get(),
                        HttpStatus.OK);
            } else {
                return noSubTaskFoundResponse(id);
            }
        } catch (Exception e) {
            return errorResponse();
        }
    }

    @GetMapping("")
    @CrossOrigin(origins = clientUrl)
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<?> getSubTaskByStatus(@RequestParam TaskStatus status) {
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

    @GetMapping("/deadline")
    @CrossOrigin(origins = clientUrl)
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<?> getSubTaskByDeadline(@RequestParam LocalDateTime deadline) {
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

    @PutMapping("/move/{id}")
    @CrossOrigin(origins = clientUrl)
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<?> updateSubTaskPlace () {
        //Missing
        return null;
    }

    @PostMapping("/")
    @CrossOrigin(origins = clientUrl)
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<?> createSubTask(@RequestBody SubTaskDTO subTaskDTO) {
        try {
            return new ResponseEntity<>(
                    subTaskService.saveNewSubTask(subTaskDTO),
                    HttpStatus.CREATED);
        } catch (Exception e) {
            return errorResponse();
        }
    }

    @PutMapping("/{id}")
    @CrossOrigin(origins = clientUrl)
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<?> updateSubTask(@PathVariable Long id, @RequestBody SubTaskDTO subTaskDTO) {
        try {
            Optional<SubTask> optSubTask = subTaskService.getSubTaskById(id);
            if (optSubTask.isPresent()) {
                return new ResponseEntity<>(
                        subTaskService.updateSubTask(optSubTask.get(), subTaskDTO),
                        HttpStatus.OK);
            } else {
                return noSubTaskFoundResponse(id);
            }
        } catch (Exception e) {
            return errorResponse();
        }
    }

    @DeleteMapping("/{id}")
    @CrossOrigin(origins = clientUrl)
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<?> deleteSubTask(@PathVariable Long id) {
        try {
            Optional<SubTask> optSubTask = subTaskService.getSubTaskById(id);
            if (optSubTask.isPresent()) {
                subTaskService.deleteSubTask(optSubTask.get());
                return new ResponseEntity<>(String.format("SubTask With Id: %d Was Deleted", id), HttpStatus.OK);
            } else {
                return noSubTaskFoundResponse(id);
            }
        } catch (Exception e) {
            return errorResponse();
        }
    }

    private ResponseEntity<String> errorResponse(){
        return new ResponseEntity<>("Something Went Wrong Ops :(", HttpStatus.INTERNAL_SERVER_ERROR);
    }

    private ResponseEntity<String> noSubTaskFoundResponse(Long id){
        return new ResponseEntity<>("No SubTask Found With Id: " + id, HttpStatus.NOT_FOUND);
    }
}

