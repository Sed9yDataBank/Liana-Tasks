package com.kanbanedchain.lianatasks.DTOs;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.kanbanedchain.lianatasks.Models.SubTask;
import com.kanbanedchain.lianatasks.Models.TaskStatus;

import java.time.LocalDateTime;

public class SubTaskDTO {

    private String content;

    private LocalDateTime deadline;

    private TaskStatus status;

    public SubTaskDTO(SubTask subTask) {
        this.content = subTask.getContent();
        this.deadline = subTask.getDeadline();
        this.status = subTask.getStatus();
    }

    public SubTaskDTO() {
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
