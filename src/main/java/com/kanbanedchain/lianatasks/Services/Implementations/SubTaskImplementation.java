package com.kanbanedchain.lianatasks.Services.Implementations;

import com.kanbanedchain.lianatasks.DTOs.SubTaskDTO;
import com.kanbanedchain.lianatasks.Models.SubTask;
import com.kanbanedchain.lianatasks.Models.Task;
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
import java.util.stream.Collectors;

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
    public SubTask moveSubTask(Long id, Long subTaskId, SubTaskDTO subTaskDTO) {
        if (!taskRepository.existsById(id)) {
            throw new  SecurityException("Task With Id : " + id + " Was Not Found");
        }
        return getSubTaskById(subTaskId).map(subTask -> {
            subTask.setTask(subTaskDTO.getTask());
            return subTaskRepository.saveAndFlush(subTask);
        }).orElseThrow(() -> new ExpressionException("SubTask With Id : " + subTaskId + " Was Not Found"));
    }

    @Override
    @Transactional
    public List<SubTask> getAllSubTasks() {
        List<SubTask> subTasksList = new ArrayList<>();
        subTaskRepository.findAll().forEach(subTasksList::add);
        return subTasksList;
    }

    @Override
    @Transactional
    public Optional<SubTask> getSubTaskById(Long id) {
        return subTaskRepository.findById(id);
    }

    @Override
    @Transactional
    public Optional<SubTask> getSubTaskByStatus(TaskStatus status) {
        return subTaskRepository.findSubTaskByStatus(status);
    }

    @Override
    @Transactional
    public Optional<SubTask> getSubTaskByDeadline(LocalDateTime deadline) {
        return subTaskRepository.findSubTaskByDeadline(deadline);
    }

    @Override
    @Transactional
    public SubTask saveNewSubTask(Long id, SubTaskDTO subTaskDTO) {
        return taskRepository.findById(id).map(task -> {
            subTaskDTO.setTask(task);
            return subTaskRepository.save(convertDTOToSubTask(subTaskDTO));
        }).orElseThrow(() -> new SecurityException("Task With " + id + " Was Not Found , Unable To Create SubTask"));
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

    @Override
    @Transactional
    public List<SubTask> listAllSubTasksByDeadline() {
        return subTaskRepository.listAllSubTasksByDeadline().collect(Collectors.toCollection(ArrayList::new));
    }
}
