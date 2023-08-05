package com.example.frontend.model;

import androidx.annotation.NonNull;

import java.io.Serializable;

public class Room implements Serializable {
    private String id;
    private long updateAt;
    private long createdAt;
    private String name;
    private String user1;
    private String user2;
    private String lastMessage;

    public Room() {
    }

    public Room(String name) {
        this.name = name;
    }

    public Room(String id, long updateAt, long createdAt, String name, String user1, String user2, String lastMessage, String image) {
        this.id = id;
        this.updateAt = updateAt;
        this.createdAt = createdAt;
        this.name = name;
        this.user1 = user1;
        this.user2 = user2;
        this.lastMessage = lastMessage;
        this.image = image;
    }

    @NonNull
    @Override
    public String toString() {
        return id+updateAt+createdAt+name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public long getUpdateAt() {
        return updateAt;
    }

    public void setUpdateAt(long updateAt) {
        this.updateAt = updateAt;
    }

    public long getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(long createdAt) {
        this.createdAt = createdAt;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUser1() {
        return user1;
    }

    public void setUser1(String user1) {
        this.user1 = user1;
    }

    public String getUser2() {
        return user2;
    }

    public void setUser2(String user2) {
        this.user2 = user2;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    private String image;

    public String getLastMessage() {
        return lastMessage;
    }

    public void setLastMessage(String lastMessage) {
        this.lastMessage = lastMessage;
    }
}
