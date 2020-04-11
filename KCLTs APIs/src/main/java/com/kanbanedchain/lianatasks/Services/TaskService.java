package com.kanbanedchain.lianatasks.Services;

import com.kanbanedchain.lianatasks.DTOs.TaskDTO;
import com.kanbanedchain.lianatasks.Models.Task;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface TaskService {

    List<Task> getAllTasks();

    Optional<Task> getTaskById(UUID taskId);

    Task saveNewTask(UUID boardId, TaskDTO taskDTO);

    Task updateTask(Task oldTask, TaskDTO newTaskDTO);

    void deleteTask(Task task);

    Optional<Task> getTaskByTitle(String title);
}
