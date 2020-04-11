package com.kanbanedchain.lianatasks.Controllers;

import com.kanbanedchain.lianatasks.DTOs.TaskDTO;
import com.kanbanedchain.lianatasks.Models.Task;
import com.kanbanedchain.lianatasks.Services.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/tasks")
@CrossOrigin(origins = "http://localhost:4200")
public class TaskController {

    @Autowired
    private TaskService taskService;

    @GetMapping("/All")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<?> getAllTasks() {
        try {
            return new ResponseEntity<>(
                    taskService.getAllTasks(),
                    HttpStatus.OK);
        } catch (Exception e) {
            return errorResponse();
        }
    }

    @GetMapping("/{taskId}")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<?> getTaskById(@PathVariable(value = "taskId") UUID taskId) {
        try {
            Optional<Task> optTask = taskService.getTaskById(taskId);
            if (optTask.isPresent()) {
                return new ResponseEntity<>(
                        optTask.get(),
                        HttpStatus.OK);
            } else {
                return noTaskFoundResponse(taskId);
            }
        } catch (Exception e) {
            return errorResponse();
        }
    }

    @PostMapping("/createTask/{boardId}")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<?> createTask(@PathVariable (value = "boardId") UUID boardId,
                                        @RequestBody TaskDTO taskDTO) {
        try {
            return new ResponseEntity<>(
                    taskService.saveNewTask(boardId, taskDTO),
                    HttpStatus.CREATED);
        } catch (Exception e) {
            return errorResponse();
        }
    }

    @PutMapping("/{taskId}")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<?> updateTask(@PathVariable(value = "taskId") UUID taskId, @RequestBody TaskDTO taskDTO) {
        try {
            Optional<Task> optTask = taskService.getTaskById(taskId);
            if (optTask.isPresent()) {
                return new ResponseEntity<>(
                        taskService.updateTask(optTask.get(), taskDTO),
                        HttpStatus.OK);
            } else {
                return noTaskFoundResponse(taskId);
            }
        } catch (Exception e) {
            return errorResponse();
        }
    }

    @PostMapping("/updateTaskStatus/{status}")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<?> updateTaskStatus(@PathVariable("status") String status) {
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/{taskId}")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<?> deleteTask(@PathVariable(value = "taskId") UUID taskId) {
        try {
            Optional<Task> optTask = taskService.getTaskById(taskId);
            if (optTask.isPresent()) {
                taskService.deleteTask(optTask.get());
                return new ResponseEntity<>(String.format("Task With Id: %d Was Deleted", taskId), HttpStatus.OK);
            } else {
                return noTaskFoundResponse(taskId);
            }
        } catch (Exception e) {
            return errorResponse();
        }
    }

    private ResponseEntity<String> errorResponse(){
        return new ResponseEntity<>("Something Went Wrong Ops :(", HttpStatus.INTERNAL_SERVER_ERROR);
    }

    private ResponseEntity<String> noTaskFoundResponse(UUID taskId){
        return new ResponseEntity<>("No Task Found With Id: " + taskId, HttpStatus.NOT_FOUND);
    }
}
