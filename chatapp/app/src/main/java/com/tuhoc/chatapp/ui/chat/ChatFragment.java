package com.tuhoc.chatapp.ui.chat;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.tuhoc.chatapp.R;
import com.tuhoc.chatapp.adapter.UserAdapter;
import com.tuhoc.chatapp.adapter.UserAvatarAdapter;
import com.tuhoc.chatapp.data.model.Chat;
import com.tuhoc.chatapp.data.model.User;
import com.tuhoc.chatapp.databinding.FragmentChatBinding;
import com.tuhoc.chatapp.interfaces.ItemClickListener;
import com.tuhoc.chatapp.ui.messages.MessagesFragment;

import java.io.Serializable;
import java.util.ArrayList;

public class ChatFragment extends Fragment implements ItemClickListener {

    private FragmentChatBinding binding;
    private UserAvatarAdapter adapter1;
    private UserAdapter adapter2;
    private ArrayList<User> list;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentChatBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initData();
        initView();
    }

    private void initView() {
        adapter1 = new UserAvatarAdapter(list, this);
        binding.rvUserAvatar.setAdapter(adapter1);

        adapter2 = new UserAdapter(list, this);
        binding.rvUser.setAdapter(adapter2);
    }

    private void initData() {
        list = new ArrayList<>();
        list.add(new User("Mạnh", "https://cdnmedia.baotintuc.vn/2015/05/04/15/56/anh9.jpg"));
        for (int i = 0; i < 10; i++) {
            User user2 = new User();
            user2.setName("Huy " + i);
            user2.setImage("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQAFoFkM-oTvVcaDj6Uberd48GuAPNcJpk46Vsizg6bMsgEOPjLWbB5JnPe3rkGh9vkol8&usqp=CAU");
            list.add(user2);
        }
    }

    @Override
    public void onItemClick(User user) {
        MessagesFragment messagesFragment = new MessagesFragment();

        // Pass the product ID to the ProductDetailsFragment
        Bundle bundle = new Bundle();
        bundle.putString("username", user.getName());
        bundle.putString("image", user.getImage());
        messagesFragment.setArguments(bundle);

        getActivity().getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.flChange, messagesFragment)
                .commit();
    }
}