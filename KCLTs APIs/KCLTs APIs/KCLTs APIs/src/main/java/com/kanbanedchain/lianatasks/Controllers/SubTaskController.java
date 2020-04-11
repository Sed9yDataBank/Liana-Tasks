package com.kanbanedchain.lianatasks.Controllers;

import com.kanbanedchain.lianatasks.DTOs.SubTaskDTO;
import com.kanbanedchain.lianatasks.Models.SubTask;
import com.kanbanedchain.lianatasks.Models.TaskStatus;
import com.kanbanedchain.lianatasks.Services.SubTaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/subTasks")
@CrossOrigin(origins = "http://localhost:4200")
public class SubTaskController {

    @Autowired
    private SubTaskService subTaskService;

    @GetMapping("/All")
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

    @GetMapping("/{subTaskId}")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<?> getSubTaskById(@PathVariable(value = "subTaskId") UUID subTaskId) {
        try {
            Optional<SubTask> optSubTask = subTaskService.getSubTaskById(subTaskId);
            if (optSubTask.isPresent()) {
                return new ResponseEntity<>(
                        optSubTask.get(),
                        HttpStatus.OK);
            } else {
                return noSubTaskFoundResponse(subTaskId);
            }
        } catch (Exception e) {
            return errorResponse();
        }
    }

    @PostMapping("/createSubTask/{taskId}")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<?> createSubTask(@PathVariable (value = "taskId") UUID taskId,
                                           @Valid @RequestBody SubTaskDTO subTaskDTO) {
        try {
            return new ResponseEntity<>(
                    subTaskService.saveNewSubTask(taskId, subTaskDTO),
                    HttpStatus.CREATED);
        } catch (Exception e) {
            return errorResponse();
        }
    }

    @PutMapping("/move/{taskId}/{subTaskId}")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<?> updateSubTaskPlace(@PathVariable(value = "taskId") UUID taskId,
                                                 @PathVariable(value = "subTaskId") UUID subTaskId,
                                                 @RequestBody SubTaskDTO subTaskDTO) {
        return new ResponseEntity<>(subTaskService.moveSubTask(taskId, subTaskId, subTaskDTO), HttpStatus.OK);
    }

    @PutMapping("/{subTaskId}")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<?> updateSubTask(@PathVariable(value = "subTaskId") UUID subTaskId,
                                           @RequestBody SubTaskDTO subTaskDTO) {
        try {
            Optional<SubTask> optSubTask = subTaskService.getSubTaskById(subTaskId);
            if (optSubTask.isPresent()) {
                return new ResponseEntity<>(
                        subTaskService.updateSubTask(optSubTask.get(), subTaskDTO),
                        HttpStatus.OK);
            } else {
                return noSubTaskFoundResponse(subTaskId);
            }
        } catch (Exception e) {
            return errorResponse();
        }
    }

    @DeleteMapping("/{subTaskId}")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<?> deleteSubTask(@PathVariable(value = "subTaskId") UUID subTaskId) {
        try {
            Optional<SubTask> optSubTask = subTaskService.getSubTaskById(subTaskId);
            if (optSubTask.isPresent()) {
                subTaskService.deleteSubTask(optSubTask.get());
                return new ResponseEntity<>("SubTask With Id: %d Was Deleted", HttpStatus.OK);
            } else {
                return noSubTaskFoundResponse(subTaskId);
            }
        } catch (Exception e) {
            return errorResponse();
        }
    }

    private ResponseEntity<String> errorResponse(){
        return new ResponseEntity<>("Something Went Wrong Ops :(", HttpStatus.INTERNAL_SERVER_ERROR);
    }

    private ResponseEntity<String> noSubTaskFoundResponse(UUID subTaskId){
        return new ResponseEntity<>("No SubTask Found With Id: " + subTaskId, HttpStatus.NOT_FOUND);
    }
}

