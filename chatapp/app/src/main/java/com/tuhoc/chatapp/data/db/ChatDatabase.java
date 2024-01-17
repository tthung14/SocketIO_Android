package com.tuhoc.chatapp.data.db;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.tuhoc.chatapp.data.model.Chat;

@Database(entities = {Chat.class}, version = 1)
public abstract class ChatDatabase extends RoomDatabase {
    public abstract ChatDao chatDao();

    private static ChatDatabase instance;

    public static ChatDatabase getInstance(final Context context) {
        if (instance == null) {
            synchronized (ChatDatabase.class) {
                instance = Room.databaseBuilder(context.getApplicationContext(), ChatDatabase.class, "chat").build();
            }
        }
        return instance;
    }

}
