package com.linhbot.app;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class ChatAdapter extends RecyclerView.Adapter<ChatAdapter.ChatViewHolder> {

    private final List<Message> messages;

    public ChatAdapter(List<Message> messages) {
        this.messages = messages;
    }

    @NonNull
    @Override
    public ChatViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_message, parent, false);
        return new ChatViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ChatViewHolder holder, int position) {
        Message msg = messages.get(position);

        if (msg.isBot) {
            // Hiển thị tin nhắn Bot
            holder.textBot.setText(msg.content);
            holder.textBot.setVisibility(View.VISIBLE);
            
            // Hiện Avatar Bot và đổi mặt dựa trên ID ảnh
            holder.imgBotAvatar.setVisibility(View.VISIBLE);
            if (msg.emoteResId != 0) {
                holder.imgBotAvatar.setImageResource(msg.emoteResId);
            } else {
                holder.imgBotAvatar.setImageResource(R.drawable.linh_avatar); // Mặc định
            }

            holder.textUser.setVisibility(View.GONE);
        } else {
            // Hiển thị tin nhắn User
            holder.textUser.setText(msg.content);
            holder.textUser.setVisibility(View.VISIBLE);
            
            holder.textBot.setVisibility(View.GONE);
            holder.imgBotAvatar.setVisibility(View.GONE); // Ẩn avatar bot khi user nhắn
        }
    }

    @Override
    public int getItemCount() {
        return messages.size();
    }

    static class ChatViewHolder extends RecyclerView.ViewHolder {
        TextView textBot, textUser;
        ImageView imgBotAvatar; // Thêm biến avatar

        public ChatViewHolder(@NonNull View itemView) {
            super(itemView);
            textBot = itemView.findViewById(R.id.textBot);
            textUser = itemView.findViewById(R.id.textUser);
            // Bạn cần thêm ID này vào file item_message.xml ở bước sau
            imgBotAvatar = itemView.findViewById(R.id.imgBotAvatar); 
        }
    }
}