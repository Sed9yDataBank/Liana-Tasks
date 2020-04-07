package com.kanbanedchain.lianatasks.DTOs;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.kanbanedchain.lianatasks.Models.Board;
import com.kanbanedchain.lianatasks.Models.Task;
import com.kanbanedchain.lianatasks.Models.TaskStatus;

import java.util.UUID;

public class TaskDTO {

    private UUID taskId;

    private String title;

    private TaskStatus status;

    public TaskDTO(Task task) {
        this.taskId = task.getTaskId();
        this.title = task.getTitle();
        this.status = task.getStatus();
    }

    public TaskDTO() {
    }

    @JsonIgnore
    private Board board;

    @JsonProperty
    public UUID getTaskId() {
        return taskId;
    }

    public Board getBoard() {
        return board;
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

    public void setBoard(Board board) {
        this.board = board;
    }
}
