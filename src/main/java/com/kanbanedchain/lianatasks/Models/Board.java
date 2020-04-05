package com.kanbanedchain.lianatasks.Models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Data
@Table(name = "boards")
public class Board extends AuditModel{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "board_Id")
    private Long Id;

    @NotNull
    @Length(min = 3, max = 10)
    private String title;

    @NotNull
    private String backgroundImagePath;

    @OneToMany(mappedBy = "board", cascade = {CascadeType.ALL}, fetch = FetchType.EAGER)
    private List<Task> tasks = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumns({
            @JoinColumn(name = "username", insertable = false, updatable = false, referencedColumnName = "username"),
            @JoinColumn(name = "email", insertable = false, updatable = false, referencedColumnName = "email")
    })
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
    private User user;

    public void addTask(Task task) {

        if (Objects.isNull(tasks)) {
            tasks = new ArrayList<>();
        }
        tasks.add(task);
    }

    public Board(Long id, String title, String backgroundImagePath) {
        this.Id = id;
        this.title = title;
        this.backgroundImagePath = backgroundImagePath;
    }

    public Board() {

    }

    public String getTitle() {
        return title;
    }

    public String getBackgroundImagePath() {
        return backgroundImagePath;
    }

    public Long getId() {
        return Id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setBackgroundImagePath(String backgroundImagePath) {
        this.backgroundImagePath = backgroundImagePath;
    }

    public void setId(Long id) {
        Id = id;
    }
}

