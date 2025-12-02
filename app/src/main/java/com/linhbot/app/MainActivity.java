package com.linhbot.app;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    // 1. Khai báo biến giao diện
    private RecyclerView recyclerChat;
    private EditText editInput;
    private Button btnSend;

    // 2. Khai báo biến dữ liệu
    private List<Message> messageList; // Danh sách tin nhắn
    private ChatAdapter chatAdapter;   // Bộ quản lý hiển thị

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main); // Nạp giao diện XML

        // 3. Ánh xạ (Tìm ID trong XML)
        recyclerChat = findViewById(R.id.recyclerChat);
        editInput = findViewById(R.id.editInput);
        btnSend = findViewById(R.id.btnSend);

        // 4. Khởi tạo danh sách & Adapter
        messageList = new ArrayList<>();
        chatAdapter = new ChatAdapter(messageList);

        // 5. Cài đặt RecyclerView (Quan trọng!)
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setStackFromEnd(true); // Luôn cuộn xuống tin nhắn mới nhất
        recyclerChat.setLayoutManager(linearLayoutManager);
        recyclerChat.setAdapter(chatAdapter);

        // 6. Thêm tin nhắn chào hỏi mặc định
        addMessageToChat("Chào bạn! Mình là Linh Bot. Bạn cần giúp gì không?", true);

        // 7. Xử lý sự kiện bấm nút Gửi
        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String text = editInput.getText().toString().trim();
                if (!text.isEmpty()) {
                    sendMessage(text);
                } else {
                    Toast.makeText(MainActivity.this, "Bạn chưa nhập gì cả!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    // Hàm xử lý gửi tin nhắn
    private void sendMessage(String text) {
        // A. Hiện tin nhắn của người dùng lên màn hình
        addMessageToChat(text, false);
        editInput.setText(""); // Xóa ô nhập liệu

        // B. Giả lập Bot trả lời (Fake AI)
        // (Sau này bạn sẽ thay đoạn này bằng code gọi AI thật)
        new android.os.Handler().postDelayed(() -> {
            String botReply = "Đã ghi nhận: " + text;
            addMessageToChat(botReply, true);
        }, 1000); // Trả lời sau 1 giây
    }

    // Hàm phụ trợ để thêm tin nhắn vào list và cập nhật giao diện
    private void addMessageToChat(String content, boolean isBot) {
        messageList.add(new Message(content, isBot));
        
        // Báo cho Adapter biết có tin mới ở vị trí cuối cùng
        chatAdapter.notifyItemInserted(messageList.size() - 1);
        
        // Tự động cuộn xuống dòng cuối cùng
        recyclerChat.smoothScrollToPosition(messageList.size() - 1);
    }
}