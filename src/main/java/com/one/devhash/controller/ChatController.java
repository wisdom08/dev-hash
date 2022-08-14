package com.one.devhash.controller;

import com.one.devhash.domain.Chat;
import com.one.devhash.dto.chatting.ChatMessage;
import com.one.devhash.service.ChatService;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;


@Controller
@RequiredArgsConstructor
public class ChatController {

    private final ChatService chatService;

    @MessageMapping("/{roomId}")
    @SendTo("/api/chats/{roomId}")
    public ChatMessage createChat(@DestinationVariable Long roomId, ChatMessage message) {
        Chat chat = chatService.createChat(roomId, message.getSender(), message.getMessage());
        return message.createChat(roomId, chat.getSender(), chat.getMessage(), chat.getCreatedAt());
    }

}
