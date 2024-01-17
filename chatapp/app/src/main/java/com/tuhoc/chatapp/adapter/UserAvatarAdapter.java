package com.tuhoc.chatapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.tuhoc.chatapp.R;
import com.tuhoc.chatapp.data.model.User;
import com.tuhoc.chatapp.interfaces.ItemClickListener;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class UserAvatarAdapter extends RecyclerView.Adapter<UserAvatarAdapter.UserViewHolder>{
    private ArrayList<User> list;
    private Context mContext;
    private ItemClickListener itemClickListener;

    public UserAvatarAdapter(ArrayList<User> list, ItemClickListener itemClickListener) {
        this.list = list;
        this.itemClickListener = itemClickListener;
    }

    @NonNull
    @Override
    public UserAvatarAdapter.UserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        this.mContext = parent.getContext();
        View view = LayoutInflater.from(mContext).inflate(R.layout.layout_item_user_avatar, parent, false);
        return new UserViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UserAvatarAdapter.UserViewHolder holder, int position) {
        User user = list.get(position);
        Glide.with(mContext).load(user.getImage()).into(holder.imgAvatar);
        holder.tvUserName.setText(String.valueOf(user.getName()));

        holder.itemView.setOnClickListener(v ->  itemClickListener.onItemClick(user));

    }

    @Override
    public int getItemCount() {
        return list != null ? list.size() : 0;
    }

    public class UserViewHolder extends RecyclerView.ViewHolder {
        CircleImageView imgAvatar;
        TextView tvUserName;
        public UserViewHolder(@NonNull View itemView) {
            super(itemView);
            imgAvatar = itemView.findViewById(R.id.imgAvatar);
            tvUserName = itemView.findViewById(R.id.tvUserName);
        }
    }
}
