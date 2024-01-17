package com.tuhoc.chatapp.data.db;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.tuhoc.chatapp.data.model.Chat;

import java.util.List;

@Dao
public interface ChatDao {
    @Insert
    void insert(Chat chat);

    @Query("SELECT * FROM chat ORDER BY id ASC")
    LiveData<List<Chat>> getAllChats();
}
