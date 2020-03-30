package com.kanbanedchain.lianatasks.Models;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.Date;

@Entity
@Data
@Table(name = "subtasks")
public class SubTask extends AuditModel {

    @NotNull
    @Size(min = 4, max = 80)
    private String content;

    @NotNull
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime deadline;

    @NotNull
    private TaskStatus status;

    @ManyToOne(targetEntity = Task.class)
    private Task task;

    public SubTask(Long id, Date createdDate, Date updatedDate, String content,
                   LocalDateTime deadline, TaskStatus status) {
        super(id, createdDate, updatedDate);
        this.content = content;
        this.deadline = deadline;
        this.status = status;
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
}
