package com.kanbanedchain.lianatasks.Services;

import com.kanbanedchain.lianatasks.DTOs.SubTaskDTO;
import com.kanbanedchain.lianatasks.Models.SubTask;
import com.kanbanedchain.lianatasks.Models.TaskStatus;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface SubTaskService {

    SubTask moveSubTask(UUID taskId, UUID subTaskId, SubTaskDTO subTaskDTO);

    List<SubTask> getAllSubTasks();

    Optional<SubTask> getSubTaskById(UUID subTaskId);

    SubTask saveNewSubTask(UUID taskId, SubTaskDTO subTaskDTO);

    SubTask updateSubTask(SubTask oldSubTask, SubTaskDTO newSubTaskDTO);

    void deleteSubTask(SubTask subTask);

    Optional<SubTask> getSubTaskByStatus(TaskStatus status);

    Optional<SubTask> getSubTaskByDeadline(LocalDateTime deadline);
}
