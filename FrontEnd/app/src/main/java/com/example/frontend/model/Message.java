package com.example.frontend.model;

public class Message {
    private String id;
    private long createdAt;
    private long updatedAt;
    private String content;
    private User user;
    private String room;

    public Message() {
        id = "";
        content = "";
    }

    public Message(String id, long createdAt, long updatedAt, String content, User user, String room) {
        this.id = id;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.content = content;
        this.user = user;
        this.room = room;
    }

    public Message(String id, String content, User user) {
        this.id = id;
        this.content = content;
        this.user = user;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public long getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(long createdAt) {
        this.createdAt = createdAt;
    }

    public long getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(long updatedAt) {
        this.updatedAt = updatedAt;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getRoom() {
        return room;
    }

    public void setRoom(String room) {
        this.room = room;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
