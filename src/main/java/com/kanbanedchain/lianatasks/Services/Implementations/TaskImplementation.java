package com.kanbanedchain.lianatasks.Services.Implementations;

import com.kanbanedchain.lianatasks.DTOs.TaskDTO;
import com.kanbanedchain.lianatasks.Models.Task;
import com.kanbanedchain.lianatasks.Repositories.BoardRepository;
import com.kanbanedchain.lianatasks.Repositories.TaskRepository;
import com.kanbanedchain.lianatasks.Services.TaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TaskImplementation implements TaskService {

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private BoardRepository boardRepository;

    @Override
    @Transactional
    public List<Task> getAllTasks() {
        List<Task> tasksList = new ArrayList<>();
        taskRepository.findAll().forEach(tasksList::add);
        return tasksList;
    }

    @Override
    @Transactional
    public Optional<Task> getTaskById(Long id) {
        return taskRepository.findById(id);
    }

    @Override
    @Transactional
    public Optional<Task> getTaskByTitle(String title) {
        return taskRepository.findByTitle(title);
    }

    @Override
    @Transactional
    public Task saveNewTask(Long id, TaskDTO taskDTO) {
        return boardRepository.findById(id).map(board -> {
            taskDTO.setBoard(board);
        return taskRepository.save(convertDTOToTask(taskDTO));
        }).orElseThrow(() -> new SecurityException("Board With " + id + " Was Not Found , Unable To Create Task"));
    }

    @Override
    @Transactional
    public Task updateTask(Task oldTask, TaskDTO newTaskDTO) {
        return taskRepository.save(updateTaskFromDTO(oldTask, newTaskDTO));
    }

    @Override
    @Transactional
    public void deleteTask(Task task) {
        taskRepository.delete(task);
    }

    private Task convertDTOToTask(TaskDTO taskDTO) {
        Task task = new Task();
        task.setTitle(taskDTO.getTitle());
        task.setStatus(taskDTO.getStatus());
        return task;
    }

    private Task updateTaskFromDTO(Task task, TaskDTO taskDTO){
        if(Optional.ofNullable(taskDTO.getTitle()).isPresent()){
            task.setTitle(taskDTO.getTitle());
        }

        if (Optional.ofNullable((taskDTO.getStatus())).isPresent()) {
            task.setStatus(taskDTO.getStatus());
        }
        return task;
    }
    
    @Override
    @Transactional
    public List<Task> listAllTasksByStatus() {
        return taskRepository.listAllTasksByStatus().collect(Collectors.toCollection(ArrayList::new));
    }
}
