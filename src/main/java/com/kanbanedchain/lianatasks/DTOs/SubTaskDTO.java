package com.kanbanedchain.lianatasks.DTOs;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.kanbanedchain.lianatasks.Models.SubTask;
import com.kanbanedchain.lianatasks.Models.Task;
import com.kanbanedchain.lianatasks.Models.TaskStatus;

import java.time.LocalDateTime;
import java.util.UUID;

public class SubTaskDTO {

    private UUID subTaskId;

    private String content;

    private LocalDateTime deadline;

    private TaskStatus status;

    public SubTaskDTO(SubTask subTask) {
        this.subTaskId = subTask.getSubTaskId();
        this.content = subTask.getContent();
        this.deadline = subTask.getDeadline();
        this.status = subTask.getStatus();
    }

    public SubTaskDTO() {
    }

    @JsonIgnore
    private Task task;


    public void setTask(Task task) {
        this.task = task;
    }

    public Task getTask() {
        return task;
    }

    @JsonProperty
    public UUID getSubTaskId() {
        return subTaskId;
    }

    @JsonProperty
    public String getContent() {
        return content;
    }

    @JsonProperty
    public LocalDateTime getDeadline() {
        return deadline;
    }

    @JsonProperty
    public TaskStatus getStatus() {
        return status;
    }
}
