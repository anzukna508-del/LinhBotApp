package com.linhbot.app;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class ChatAdapter extends RecyclerView.Adapter<ChatAdapter.ChatViewHolder> {

    private final List<Message> messages;

    // 1. Hàm tạo Adapter: Nhận danh sách tin nhắn
    public ChatAdapter(List<Message> messages) {
        this.messages = messages;
    }

    // 2. Tạo ViewHolder (Giống như tạo DOM Element)
    @NonNull
    @Override
    public ChatViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Nạp item_message.xml
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_message, parent, false);
        return new ChatViewHolder(view);
    }

    // 3. Liên kết dữ liệu với giao diện (Giống như thêm innerHTML)
    @Override
    public void onBindViewHolder(@NonNull ChatViewHolder holder, int position) {
        Message msg = messages.get(position);

        if (msg.isBot) {
            // Hiển thị tin nhắn Bot (bên trái)
            holder.textBot.setText(msg.content);
            holder.textBot.setVisibility(View.VISIBLE);
            holder.textUser.setVisibility(View.GONE);
        } else {
            // Hiển thị tin nhắn User (bên phải)
            holder.textUser.setText(msg.content);
            holder.textUser.setVisibility(View.VISIBLE);
            holder.textBot.setVisibility(View.GONE);
        }
    }

    // 4. Lấy tổng số tin nhắn
    @Override
    public int getItemCount() {
        return messages.size();
    }

    // Class giữ các thành phần UI cho mỗi tin nhắn
    static class ChatViewHolder extends RecyclerView.ViewHolder {
        TextView textBot, textUser;

        public ChatViewHolder(@NonNull View itemView) {
            super(itemView);
            // Ánh xạ các thành phần từ item_message.xml
            textBot = itemView.findViewById(R.id.textBot);
            textUser = itemView.findViewById(R.id.textUser);
        }
    }
}