package com.kanbanedchain.lianatasks.Services;

import com.kanbanedchain.lianatasks.DTOs.TaskDTO;
import com.kanbanedchain.lianatasks.Models.Task;

import java.util.List;
import java.util.Optional;

public interface TaskService {

    List<Task> getAllTasks();

    Optional<Task> getTaskById(Long id);

    Optional<Task> getTaskByTitle(String title);

    Task saveNewTask(TaskDTO taskDTO);

    Task updateTask(Task oldTask, TaskDTO newTaskDTO);

    void deleteTask(Task task);

    List<Task> listAllTasksByStatus();
}
