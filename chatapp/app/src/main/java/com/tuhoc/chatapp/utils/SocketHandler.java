package com.tuhoc.chatapp.utils;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.google.gson.Gson;
import com.tuhoc.chatapp.data.model.Chat;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.URISyntaxException;

import io.socket.client.IO;
import io.socket.client.Socket;
import io.socket.emitter.Emitter;

public class SocketHandler {
    private Socket socket;

    private final MutableLiveData<Chat> _onNewChat = new MutableLiveData<>();
    public LiveData<Chat> onNewChat = _onNewChat;
    private static final String SOCKET_URL = "http://192.168.1.206:3000/";

    public SocketHandler() {
        try {
            socket = IO.socket(SOCKET_URL);
            socket.connect();

            registerOnNewChat();

        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }

    private void registerOnNewChat() {
        socket.on(CHAT_KEYS.BROADCAST, args -> {
            if (args != null && args.length > 0) {
                Object data = args[0];
                Log.d("DATADEBUG", String.valueOf(data));
                if (!String.valueOf(data).isEmpty()) {
                    Chat chat = new Gson().fromJson(String.valueOf(data), Chat.class);
                    _onNewChat.postValue(chat);
                }
            }
        });
    }

    public void disconnectSocket() {
        socket.disconnect();
        socket.off();
    }

    public void emitChat(Chat chat) {
        String jsonStr = new Gson().toJson(chat, Chat.class);
        socket.emit(CHAT_KEYS.NEW_MESSAGE, jsonStr);
    }

    private static class CHAT_KEYS {
        static final String NEW_MESSAGE = "new_message";
        static final String BROADCAST = "broadcast";
    }
}
