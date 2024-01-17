package com.tuhoc.chatapp.data.model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.Objects;

@Entity(tableName = "chat")
public class Chat {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private String username;
    private String text;
    private boolean isSelf = false;

    public Chat(String username, String text) {
        this.username = username;
        this.text = text;
    }

    public Chat(String username, String text, boolean isSelf) {
        this.username = username;
        this.text = text;
        this.isSelf = isSelf;
    }

    public Chat() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public boolean isSelf() {
        return isSelf;
    }

    public void setSelf(boolean self) {
        isSelf = self;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;

        Chat other = (Chat) obj;

        return username.equals(other.username) &&
                text.equals(other.text) &&
                isSelf == other.isSelf;
    }
}
