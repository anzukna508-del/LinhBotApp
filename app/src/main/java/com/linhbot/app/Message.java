package com.linhbot.app;

// Khai báo một lớp (Class) giống như một Object trong JS
public class Message {
    public String content;
    public boolean isBot; // True = Tin nhắn của Bot, False = Tin nhắn của User

    // Hàm tạo (Constructor)
    public Message(String content, boolean isBot) {
        this.content = content;
        this.isBot = isBot;
    }
}