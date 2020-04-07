package com.kanbanedchain.lianatasks.Models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Data;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.*;

@Entity
@Data
@Table(name = "boards")
public class Board extends AuditModel{

    @Id
    @Column(name = "board_Id")
    private UUID boardId;

    @NotNull
    private UUID admin;

    @NotNull
    @Length(min = 3, max = 10)
    private String title;

    private String backgroundImagePath;

    @OneToMany(mappedBy = "board", cascade = {CascadeType.ALL}, fetch = FetchType.EAGER)
    private List<Task> tasks = new ArrayList<>();

    @ManyToMany
    @JsonManagedReference
    private List<User> users;

    public void addTask(Task task) {

        if (Objects.isNull(tasks)) {
            tasks = new ArrayList<>();
        }
        tasks.add(task);
    }

    public Board(UUID boardId, String title, String backgroundImagePath, UUID admin) {
        this.boardId = boardId;
        this.title = title;
        this.backgroundImagePath = backgroundImagePath;
        this.admin = admin;
    }

    public Board() {
    }

    public String getTitle() {
        return title;
    }

    public Optional<String> getBackgroundImagePath() {
        return Optional.ofNullable(backgroundImagePath);
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setBackgroundImagePath(String backgroundImagePath) {
        this.backgroundImagePath = backgroundImagePath;
    }

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

    public void setAdmin(UUID admin) {
        this.admin = admin;
    }

    public UUID getAdmin() {
        return admin;
    }

    public UUID getBoardId() {
        return boardId;
    }

    public void setBoardId(UUID boardId) {
        this.boardId = boardId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Board that = (Board) o;
        return Objects.equals(boardId, that.boardId) &&
                Objects.equals(title, that.title) &&
                Objects.equals(admin, that.admin) &&
                Objects.equals(backgroundImagePath, that.backgroundImagePath);
    }

    @Override
    public int hashCode() {
        return Objects.hash(boardId, admin, title, backgroundImagePath);
    }
}

