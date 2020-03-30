package com.kanbanedchain.lianatasks.DTOs;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.kanbanedchain.lianatasks.Models.Task;
import com.kanbanedchain.lianatasks.Models.TaskStatus;

public class TaskDTO {

    private String title;

    private TaskStatus status;

    public TaskDTO(Task task) {
        this.title = task.getTitle();
        this.status = task.getStatus();
    }

    public TaskDTO() {
    }

    @JsonProperty
    public String getTitle() {
        return title;
    }

    @JsonProperty
    public TaskStatus getStatus() {
        return status;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setStatus(TaskStatus status) {
        this.status = status;
    }
}
