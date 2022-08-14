package com.one.devhash.dto.chatting;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class ChatResponseDto {
    private final String sender;
    private final String recipient;
    private final LocalDateTime createdAt;

    private ChatResponseDto(String sender, String recipient, LocalDateTime createdAt) {
        this.sender = sender;
        this.recipient = recipient;
        this.createdAt = createdAt;
    }

    public static ChatResponseDto createNewDto(String sender, String recipient, LocalDateTime createdAt) {
        return new ChatResponseDto(sender, recipient, createdAt);
    }
}
