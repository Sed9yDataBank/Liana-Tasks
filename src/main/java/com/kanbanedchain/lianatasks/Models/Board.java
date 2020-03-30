package com.kanbanedchain.lianatasks.Models;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;

@Entity
@Data
@Table(name = "boards")
public class Board extends AuditModel{

    @NotNull
    @Length(min = 3, max = 10)
    private String title;

    @NotNull
    private String backgroundImagePath;

    @OneToMany(
            mappedBy = "board",
            cascade = {CascadeType.ALL},
            fetch = FetchType.EAGER)
    @JoinColumn(name = "board_id")
    private List<Task> tasks;

    @ManyToOne(targetEntity = User.class)
    private User user;

    public Board(Long id, Date createdDate, Date updatedDate, String title, String backgroundImagePath) {
        super(id, createdDate, updatedDate);
        this.title = title;
        this.backgroundImagePath = backgroundImagePath;
    }

    public String getTitle() {
        return title;
    }

    public String getBackgroundImagePath() {
        return backgroundImagePath;
    }
}
