package com.one.devhash.dto.chatting;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class ChatResponseDto {
    private final Long id;
    private final String message;
    private final String sender;
    private final LocalDateTime createdAt;

    private ChatResponseDto(Long id, String message, String sender, LocalDateTime createdAt) {
        this.id = id;
        this.message = message;
        this.sender = sender;
        this.createdAt = createdAt;
    }

    public static ChatResponseDto createNewDto(Long id, String message, String sender, LocalDateTime createdAt) {
        return new ChatResponseDto(id, message, sender, createdAt);
    }
}
