package com.kanbanedchain.lianatasks.Services;

import com.kanbanedchain.lianatasks.DTOs.SubTaskDTO;
import com.kanbanedchain.lianatasks.DTOs.TaskDTO;
import com.kanbanedchain.lianatasks.Models.SubTask;
import com.kanbanedchain.lianatasks.Models.Task;
import com.kanbanedchain.lianatasks.Models.TaskStatus;

import java.util.List;
import java.util.Optional;

public interface SubTaskService {

    SubTask moveSubTask(Long boardId, Long taskId);

    List<SubTask> getAllSubTasks();

    Optional<SubTask> getSubTaskById(Long id);

    Optional<SubTask> getSubTaskByStatus(TaskStatus status);

    SubTask saveNewSubTask(SubTaskDTO subTaskDTO);

    SubTask updateSubTask(SubTask oldSubTask, SubTaskDTO newSubTaskDTO);

    void deleteSubTask(SubTask subTask);

    List<SubTask> listAllSubTasksByDeadline();
}
