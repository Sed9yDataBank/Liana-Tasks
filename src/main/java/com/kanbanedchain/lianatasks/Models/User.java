package com.kanbanedchain.lianatasks.Models;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;

@Entity
@Data
@Table(name = "users")
public class User extends AuditModel {

    @NotNull
    @Column(unique = true)
    @Length(min = 4, max = 30)
    private String username;

    @NotNull
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;

    @OneToMany(
            mappedBy = "user",
            cascade = {CascadeType.ALL},
            fetch = FetchType.EAGER)
    @JoinColumn(name = "board_id")
    private List<Board> boards;

    public User(Long id, Date createdDate, Date updatedDate, String username, String password) {
        super(id, createdDate, updatedDate);
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }
}
