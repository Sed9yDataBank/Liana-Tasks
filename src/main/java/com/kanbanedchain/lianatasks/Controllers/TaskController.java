package com.kanbanedchain.lianatasks.Controllers;

import com.kanbanedchain.lianatasks.DTOs.TaskDTO;
import com.kanbanedchain.lianatasks.Models.Task;
import com.kanbanedchain.lianatasks.Services.TaskService;
import com.kanbanedchain.lianatasks.Utils.Client;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/tasks")
@CrossOrigin(origins = "http://localhost:4200")
public class TaskController {
    final String clientUrl = Client.clientUrl;

    @Autowired
    private TaskService taskService;

    @GetMapping("/All")
    @CrossOrigin(origins = clientUrl)
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

    @GetMapping("/{id}")
    @CrossOrigin(origins = clientUrl)
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<?> getTask(@PathVariable Long id) {
        try {
            Optional<Task> optTask = taskService.getTaskById(id);
            if (optTask.isPresent()) {
                return new ResponseEntity<>(
                        optTask.get(),
                        HttpStatus.OK);
            } else {
                return noTaskFoundResponse(id);
            }
        } catch (Exception e) {
            return errorResponse();
        }
    }

    @GetMapping("")
    @CrossOrigin(origins = clientUrl)
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<?> getTaskByTitle(@RequestParam String title) {
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

    @PostMapping("/")
    @CrossOrigin(origins = clientUrl)
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<?> createTask(@RequestBody TaskDTO taskDTO) {
        try {
            return new ResponseEntity<>(
                    taskService.saveNewTask(taskDTO),
                    HttpStatus.CREATED);
        } catch (Exception e) {
            return errorResponse();
        }
    }

    @PutMapping("/{id}")
    @CrossOrigin(origins = clientUrl)
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<?> updateTask(@PathVariable Long id, @RequestBody TaskDTO taskDTO) {
        try {
            Optional<Task> optTask = taskService.getTaskById(id);
            if (optTask.isPresent()) {
                return new ResponseEntity<>(
                        taskService.updateTask(optTask.get(), taskDTO),
                        HttpStatus.OK);
            } else {
                return noTaskFoundResponse(id);
            }
        } catch (Exception e) {
            return errorResponse();
        }
    }

    @DeleteMapping("/{id}")
    @CrossOrigin(origins = clientUrl)
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<?> deleteTask(@PathVariable Long id) {
        try {
            Optional<Task> optTask = taskService.getTaskById(id);
            if (optTask.isPresent()) {
                taskService.deleteTask(optTask.get());
                return new ResponseEntity<>(String.format("Task With Id: %d Was Deleted", id), HttpStatus.OK);
            } else {
                return noTaskFoundResponse(id);
            }
        } catch (Exception e) {
            return errorResponse();
        }
    }

    private ResponseEntity<String> errorResponse(){
        return new ResponseEntity<>("Something Went Wrong Ops :(", HttpStatus.INTERNAL_SERVER_ERROR);
    }

    private ResponseEntity<String> noTaskFoundResponse(Long id){
        return new ResponseEntity<>("No Task Found With Id: " + id, HttpStatus.NOT_FOUND);
    }
}
