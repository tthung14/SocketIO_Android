package com.tuhoc.chatapp.ui.messages;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;

import com.bumptech.glide.Glide;
import com.tuhoc.chatapp.adapter.ChatAdapter;
import com.tuhoc.chatapp.data.db.ChatDatabase;
import com.tuhoc.chatapp.data.model.Chat;
import com.tuhoc.chatapp.databinding.FragmentMessagesBinding;
import com.tuhoc.chatapp.utils.SocketHandler;

import java.util.ArrayList;
import java.util.List;

public class MessagesFragment extends Fragment {
    private FragmentMessagesBinding binding;
    private SocketHandler socketHandler;
    private ChatAdapter chatAdapter;
    private List<Chat> chatList = new ArrayList<>();
    private String userName = "";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentMessagesBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        userName = getArguments().getString("username");
        String image = getArguments().getString("image");
        binding.tvUserName.setText(userName);
        Glide.with(requireContext())
                .load(image)
                .into(binding.imgAvatar);

        if (userName.isEmpty()) {
            requireActivity().finish();
        } else {
            socketHandler = new SocketHandler();

            chatAdapter = new ChatAdapter();

            binding.rvChat.setLayoutManager(new LinearLayoutManager(requireContext()));
            binding.rvChat.setAdapter(chatAdapter);

            binding.btnSend.setOnClickListener(v -> {
                String message = binding.edtMessage.getText().toString();
                if (!message.isEmpty()) {
                    if (userName.equals("Hùng") || userName.equals("Mạnh")) {
                        Chat chat = new Chat(userName, message);
                        socketHandler.emitChat(chat);
                    }
                    binding.edtMessage.setText("");
                }

                // ẩn bàn phím khi click
                InputMethodManager mgr = (InputMethodManager) requireContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                mgr.hideSoftInputFromWindow(binding.edtMessage.getWindowToken(), 0);

            });

            socketHandler.onNewChat.observe(getViewLifecycleOwner(), new Observer<Chat>() {
                @Override
                public void onChanged(Chat it) {
                    Chat chat = new Chat(it.getUsername(), it.getText());
                    chat.setSelf(it.getUsername().equals(userName));

                    // Lưu tin nhắn vào cơ sở dữ liệu
//                    saveChatToDatabase(chat);

                    chatList.add(chat);
                    chatAdapter.submitChat(chatList);
                    binding.rvChat.scrollToPosition(chatList.size() - 1);
                }
            });
        }

        binding.imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().getSupportFragmentManager()
                        .beginTransaction()
                        .remove(MessagesFragment.this) // Loại bỏ fragment hiện tại
                        .commit();
            }
        });

//        loadChatHistory();
    }

    private void loadChatHistory() {
        ChatDatabase.getInstance(requireContext()).chatDao().getAllChats().observe(getViewLifecycleOwner(), new Observer<List<Chat>>() {
            @Override
            public void onChanged(List<Chat> chats) {
                // Xử lý danh sách tin nhắn và cập nhật adapter
                chatList.clear();
                chatList.addAll(chats);
                chatAdapter.submitChat(chatList);
                binding.rvChat.scrollToPosition(chatList.size() - 1);
            }
        });
    }
    private void saveChatToDatabase(Chat chat) {
        ChatDatabase.getInstance(requireContext()).chatDao().insert(chat);
    }

    @Override
    public void onDestroyView() {
        socketHandler.disconnectSocket();
        super.onDestroyView();
    }
}