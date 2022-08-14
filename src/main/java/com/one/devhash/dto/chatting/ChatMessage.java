package com.one.devhash.dto.chatting;

import lombok.Getter;

import java.time.LocalDateTime;

public class ChatMessage {
    private Long roomId;
    @Getter private String sender;
    @Getter private String message;
    private LocalDateTime createdAt;


    protected ChatMessage () {}

    private ChatMessage(Long roomId, String sender, String message, LocalDateTime createdAt) {
        this.roomId = roomId;
        this.sender = sender;
        this.message = message;
        this.createdAt = createdAt;
    }

    public ChatMessage createChat(Long roomId, String sender, String message, LocalDateTime createdAt) {
        return new ChatMessage(roomId, sender, message, createdAt);
    }

}
