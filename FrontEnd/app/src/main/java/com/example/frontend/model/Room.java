package com.example.frontend.model;

import androidx.annotation.NonNull;

import java.io.Serializable;

public class Room implements Serializable {
    private String id;
    private long updateAt;
    private long createdAt;
    private String name;

    public Room() {
    }

    public Room(String id, long updateAt, long createdAt, String name) {
        this.id = id;
        this.updateAt = updateAt;
        this.createdAt = createdAt;
        this.name = name;
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
}
