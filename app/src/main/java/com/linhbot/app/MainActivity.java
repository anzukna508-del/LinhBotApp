package com.linhbot.app;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerChat;
    private EditText editInput;
    private Button btnSend; // Thực ra là ImageView nút gửi
    private ImageView imgHeaderAvatar; // Avatar trên thanh tiêu đề
    private TextView txtStatus; // Dòng chữ trạng thái (Bot đang nhập...)

    private List<Message> messageList;
    private ChatAdapter chatAdapter;
    
    // Map lưu các kịch bản trả lời
    private Map<String, String> scriptDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // 1. Ánh xạ
        recyclerChat = findViewById(R.id.recyclerChat);
        editInput = findViewById(R.id.editInput);
        btnSend = findViewById(R.id.btnSend); // Nút gửi (ImageView trong XML)
        imgHeaderAvatar = findViewById(R.id.imgHeaderAvatar); // Avatar to trên đầu
        
        // Bạn có thể thêm TextView id txtStatus vào XML nếu muốn hiện chữ "Đang nhập..."
        // txtStatus = findViewById(R.id.txtStatus); 

        // 2. Setup RecyclerView
        messageList = new ArrayList<>();
        chatAdapter = new ChatAdapter(messageList);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setStackFromEnd(true);
        recyclerChat.setLayoutManager(layoutManager);
        recyclerChat.setAdapter(chatAdapter);

        // 3. Nạp dữ liệu kịch bản (Fake AI)
        loadScripts();

        // 4. Lời chào đầu tiên
        processBotReply("Chào đằng ấy! Lâu lắm không gặp. [happy]");

        // 5. Sự kiện bấm gửi
        btnSend.setOnClickListener(v -> {
            String text = editInput.getText().toString().trim();
            if (!text.isEmpty()) {
                sendMessage(text);
            }
        });
    }

    private void sendMessage(String text) {
        // Hiện tin nhắn User
        messageList.add(new Message(text, false, 0));
        chatAdapter.notifyItemInserted(messageList.size() - 1);
        recyclerChat.smoothScrollToPosition(messageList.size() - 1);
        editInput.setText("");

        // Giả lập Bot đang suy nghĩ (Delay 1-2 giây)
        new Handler().postDelayed(() -> {
            String reply = getHardcodedReply(text);
            processBotReply(reply);
        }, 1200);
    }

    // Hàm xử lý tin nhắn Bot: Tách nội dung và cảm xúc
    private void processBotReply(String rawReply) {
        String content = rawReply;
        int emoteId = R.drawable.linh_avatar; // Mặc định (hoặc neutral)

        // Logic tách tag cảm xúc giống file JS của bạn
        // Ví dụ: "Đừng đùa nữa [angry]" -> Nội dung: "Đừng đùa nữa", Mặt: angry.jpg
        
        if (rawReply.contains("[happy]")) {
            content = rawReply.replace("[happy]", "").trim();
            emoteId = R.drawable.happy; 
        } else if (rawReply.contains("[angry]")) {
            content = rawReply.replace("[angry]", "").trim();
            emoteId = R.drawable.angry;
        } else if (rawReply.contains("[sad]") || rawReply.contains("[pouty]")) {
            content = rawReply.replace("[sad]", "").replace("[pouty]", "").trim();
            emoteId = R.drawable.pouty_crying; // Dùng ảnh bạn đã upload
        } else if (rawReply.contains("[shy]")) {
            content = rawReply.replace("[shy]", "").trim();
            emoteId = R.drawable.shy_turn_away;
        } else if (rawReply.contains("[tired]")) {
            content = rawReply.replace("[tired]", "").trim();
            emoteId = R.drawable.tired;
        } else if (rawReply.contains("[surprised]")) {
            content = rawReply.replace("[surprised]", "").trim();
            emoteId = R.drawable.surprise;
        }

        // Cập nhật Avatar trên Header (Mặt bot thay đổi theo cảm xúc)
        imgHeaderAvatar.setImageResource(emoteId);

        // Thêm tin nhắn vào danh sách
        messageList.add(new Message(content, true, emoteId));
        chatAdapter.notifyItemInserted(messageList.size() - 1);
        recyclerChat.smoothScrollToPosition(messageList.size() - 1);
    }

    // --- KỊCH BẢN CỨNG (Thay thế AI) ---
    private void loadScripts() {
        scriptDatabase = new HashMap<>();
        
        // Dữ liệu mẫu (Bạn có thể thêm tùy thích)
        scriptDatabase.put("hello", "Chào cưng! Hôm nay thế nào? [happy]");
        scriptDatabase.put("chào", "Chào cưng! Hôm nay thế nào? [happy]");
        scriptDatabase.put("hi", "Hi nhóc! [wink]");
        
        scriptDatabase.put("buồn", "Sao lại buồn? Ai bắt nạt à? [pouty]");
        scriptDatabase.put("chán", "Chán thì chơi game với chị không? [smirk]");
        
        scriptDatabase.put("yêu em", "Gớm, lại văn vở rồi. [shy]");
        scriptDatabase.put("xinh", "Chuyện, chị mà lại! [happy]");
        
        scriptDatabase.put("ngu", "Nói ai ngu đấy? Muốn ăn đòn không? [angry]");
        scriptDatabase.put("cút", "Ơ cái thái độ lồi lõm gì đấy? [angry]");
        
        scriptDatabase.put("mệt", "Mệt thì nghỉ đi, đừng cố quá. [tired]");
    }

    private String getHardcodedReply(String input) {
        String lowerInput = input.toLowerCase();
        
        // 1. Tìm chính xác trong Map
        if (scriptDatabase.containsKey(lowerInput)) {
            return scriptDatabase.get(lowerInput);
        }
        
        // 2. Tìm gần đúng (Keyword)
        for (String key : scriptDatabase.keySet()) {
            if (lowerInput.contains(key)) {
                return scriptDatabase.get(key);
            }
        }
        
        // 3. Nếu không hiểu
        return "Nói gì cơ? Chị chưa load kịp... [tired]";
    }
}