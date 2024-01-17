package com.tuhoc.chatapp.adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.recyclerview.widget.AsyncListDiffer;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.tuhoc.chatapp.data.model.Chat;
import com.tuhoc.chatapp.databinding.ItemChatOtherBinding;
import com.tuhoc.chatapp.databinding.ItemChatSelfBinding;

import java.util.List;

public class ChatAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
    private static final int ITEM_SELF = 1;
    private static final int ITEM_OTHER = 2;

    private final DiffUtil.ItemCallback<Chat> diffCallback = new DiffUtil.ItemCallback<Chat>() {
        @Override
        public boolean areItemsTheSame(Chat oldItem, Chat newItem) {
            return oldItem == newItem;
        }

        @Override
        public boolean areContentsTheSame(Chat oldItem, Chat newItem) {
            return oldItem.equals(newItem);
        }
    };

    private final AsyncListDiffer<Chat> differ = new AsyncListDiffer<>(this, diffCallback);

    public void submitChat(List<Chat> chats) {
        differ.submitList(chats);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == ITEM_SELF) {
            ItemChatSelfBinding binding = ItemChatSelfBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
            return new SelfChatItemViewHolder(binding);
        } else {
            ItemChatOtherBinding binding = ItemChatOtherBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
            return new OtherChatItemViewHolder(binding);
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        Chat chat = differ.getCurrentList().get(position);
        if (chat.isSelf()) {
            ((SelfChatItemViewHolder) holder).bind(chat);
        } else {
            ((OtherChatItemViewHolder) holder).bind(chat);
        }
    }

    @Override
    public int getItemViewType(int position) {
        Chat chat = differ.getCurrentList().get(position);
        return chat.isSelf() ? ITEM_SELF : ITEM_OTHER;
    }

    @Override
    public int getItemCount() {
        return differ.getCurrentList().size();
    }

    public static class OtherChatItemViewHolder extends RecyclerView.ViewHolder {
        private final ItemChatOtherBinding binding;

        public OtherChatItemViewHolder(ItemChatOtherBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind(Chat chat) {
//            binding.name.setText(chat.getUsername());
            binding.tvMessageOther.setText(chat.getText());
        }
    }

    public static class SelfChatItemViewHolder extends RecyclerView.ViewHolder {
        private final ItemChatSelfBinding binding;

        public SelfChatItemViewHolder(ItemChatSelfBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind(Chat chat) {
//            binding.name.setText("You");
            binding.tvMessageSelf.setText(chat.getText());
        }
    }
}
