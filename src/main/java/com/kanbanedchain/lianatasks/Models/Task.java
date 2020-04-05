package com.kanbanedchain.lianatasks.Models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@Table(name = "tasks")
public class Task extends AuditModel{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "task_Id")
    private Long Id;

    @NotNull
    @Size(min = 3, max = 25)
    private String title;

    @Enumerated(EnumType.STRING)
    @NotNull
    private TaskStatus status;

    @OneToMany(mappedBy = "task", cascade = {CascadeType.ALL}, fetch = FetchType.EAGER)
    private List<SubTask> subTasks = new ArrayList<>();


    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "board_Id", nullable = false, referencedColumnName = "board_Id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
    private Board board;

    public Task(Long id, String title, TaskStatus status) {
        this.Id = id;
        this.title = title;
        this.status = status;
    }

    public Task() {

    }

    public String getTitle() {
        return title;
    }

    public TaskStatus getStatus() {
        return status;
    }

    public Long getId() {
        return Id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setStatus(TaskStatus status) {
        this.status = status;
    }

    public void setId(Long id) {
        Id = id;
    }
}
