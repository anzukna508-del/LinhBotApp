package com.linhbot.app;

public class Message {
    public String content;
    public boolean isBot;
    public int emoteResId; // Lưu ID ảnh cảm xúc (ví dụ: R.drawable.happy)

    public Message(String content, boolean isBot, int emoteResId) {
        this.content = content;
        this.isBot = isBot;
        this.emoteResId = emoteResId;
    }
}