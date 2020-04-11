package com.kanbanedchain.lianatasks.Services.Implementations;

import com.kanbanedchain.lianatasks.DTOs.SubTaskDTO;
import com.kanbanedchain.lianatasks.Models.SubTask;
import com.kanbanedchain.lianatasks.Models.TaskStatus;
import com.kanbanedchain.lianatasks.Repositories.BoardRepository;
import com.kanbanedchain.lianatasks.Repositories.SubTaskRepository;
import com.kanbanedchain.lianatasks.Repositories.TaskRepository;
import com.kanbanedchain.lianatasks.Services.SubTaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.expression.ExpressionException;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class SubTaskImplementation implements SubTaskService {

    @Autowired
    private SubTaskRepository subTaskRepository;

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private BoardRepository boardRepository;

    @Autowired
    public SubTaskImplementation(BoardRepository boardRepository, TaskRepository taskRepository,
                                 SubTaskRepository subTaskRepository) {
        Assert.notNull(boardRepository);
        Assert.notNull(taskRepository);
        Assert.notNull((subTaskRepository));

        this.boardRepository = boardRepository;
        this.taskRepository = taskRepository;
        this.subTaskRepository = subTaskRepository;
    }

    @Override
    @Transactional
    public SubTask moveSubTask(UUID taskId, UUID subTaskId, SubTaskDTO subTaskDTO) {
        if (!taskRepository.existsById(taskId)) {
            throw new  SecurityException("Task With Id : " + taskId + " Was Not Found");
        }
        return getSubTaskById(subTaskId).map(subTask -> {
            subTask.setTask(subTaskDTO.getTask());
            return subTaskRepository.saveAndFlush(subTask);
        }).orElseThrow(() -> new ExpressionException("SubTask With Id : " + subTaskId + " Was Not Found"));
    }

    @Override
    @Transactional
    public List<SubTask> getAllSubTasks() {
        return new ArrayList<SubTask>(subTaskRepository.findAll());
    }

    @Override
    @Transactional
    public Optional<SubTask> getSubTaskByStatus(TaskStatus status) {
        return subTaskRepository.findSubTaskByStatus(status);
    }

    @Override
    @Transactional
    public Optional<SubTask> getSubTaskById(UUID subTaskId) {
        return subTaskRepository.findById(subTaskId);
    }

    @Override
    @Transactional
    public Optional<SubTask> getSubTaskByDeadline(LocalDateTime deadline) {
        return subTaskRepository.findSubTaskByDeadline(deadline);
    }

    @Override
    @Transactional
    public SubTask saveNewSubTask(UUID taskId, SubTaskDTO subTaskDTO) {
        return taskRepository.findById(taskId).map(task -> {
            subTaskDTO.setTask(task);
            return subTaskRepository.save(convertDTOToSubTask(subTaskDTO));
        }).orElseThrow(() -> new SecurityException("Task With " + taskId + " Was Not Found ," +
                " Unable To Create SubTask"));
    }

    @Override
    @Transactional
    public SubTask updateSubTask(SubTask oldSubTask, SubTaskDTO newSubTaskDTO) {
        return subTaskRepository.save(updateSubTaskFromDTO(oldSubTask, newSubTaskDTO));
    }

    @Override
    @Transactional
    public void deleteSubTask(SubTask subTask) {
        subTaskRepository.delete(subTask);
    }

    private SubTask convertDTOToSubTask(SubTaskDTO subTaskDTO) {
        SubTask subTask = new SubTask();
        subTask.setContent(subTaskDTO.getContent());
        subTask.setDeadline(subTaskDTO.getDeadline());
        subTask.setStatus(subTaskDTO.getStatus());
        return subTask;
    }

    private SubTask updateSubTaskFromDTO(SubTask subTask, SubTaskDTO subTaskDTO){
        if(Optional.ofNullable(subTaskDTO.getContent()).isPresent()){
            subTask.setContent(subTaskDTO.getContent());
        }

        if (Optional.ofNullable((subTaskDTO.getDeadline())).isPresent()) {
            subTask.setDeadline(subTaskDTO.getDeadline());
        }

        if (Optional.ofNullable((subTaskDTO.getStatus())).isPresent()) {
            subTask.setStatus(subTaskDTO.getStatus());
        }
        return subTask;
    }
}
