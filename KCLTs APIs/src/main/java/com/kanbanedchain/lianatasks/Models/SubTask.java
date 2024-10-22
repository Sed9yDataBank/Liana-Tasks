package com.kanbanedchain.lianatasks.Models;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Data
@Table(name = "subTasks")
public class SubTask extends AuditModel {

    @Id
    @Column(name = "subTask_Id")
    private UUID subTaskId;

    @NotNull
    @Size(min = 4, max = 80)
    private String content;

    @NotNull
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime deadline;

    @NotNull
    private TaskStatus status;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "task_Id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
    private Task task;

    public SubTask(UUID subTaskId, String content,
                   LocalDateTime deadline, TaskStatus status) {
        this.subTaskId = subTaskId;
        this.content = content;
        this.deadline = deadline;
        this.status = status;
    }

    public SubTask() {

    }

    public String getContent() {
        return content;
    }

    public LocalDateTime getDeadline() {
        return deadline;
    }

    public TaskStatus getStatus() {
        return status;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setDeadline(LocalDateTime deadline) {
        this.deadline = deadline;
    }

    public void setStatus(TaskStatus status) {
        this.status = status;
    }

    public Task getTask() {
        return task;
    }

    public void setTask(Task task) {
        this.task = task;
    }

    public UUID getSubTaskId() {
        return subTaskId;
    }

    public void setSubTaskId(UUID subTaskId) {
        this.subTaskId = subTaskId;
    }
}
