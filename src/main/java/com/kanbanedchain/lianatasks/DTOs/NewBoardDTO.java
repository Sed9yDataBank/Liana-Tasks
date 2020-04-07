package com.kanbanedchain.lianatasks.DTOs;

import java.util.UUID;

public class NewBoardDTO {

    private UUID boardId;
    private String title;
    private String backgroundImagePath;
    private UUID[] users;
    private UUID admin;



    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBackgroundImagePath() {
        return backgroundImagePath;
    }

    public void setBackgroundImagePath(String backgroundImagePath) {
        this.backgroundImagePath = backgroundImagePath;
    }

    public UUID getBoardId() {
        return boardId;
    }

    public void setBoardId(UUID boardId) {
        this.boardId = boardId;
    }

    public UUID[] getUsers() {
        return users;
    }

    public void setUsers(UUID[] users) {
        this.users = users;
    }

    public UUID getAdmin() {
        return admin;
    }

    public void setAdmin(UUID admin) {
        this.admin = admin;
    }
}
