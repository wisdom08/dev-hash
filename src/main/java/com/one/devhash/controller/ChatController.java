package com.one.devhash.controller;

import com.one.devhash.domain.Chat;
import com.one.devhash.dto.chatting.ChatMessage;
import com.one.devhash.dto.chatting.ChatResponseDto;
import com.one.devhash.global.response.ApiUtils;
import com.one.devhash.global.response.CommonResponse;
import com.one.devhash.service.ChatService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.List;


@Controller
@RequiredArgsConstructor
public class ChatController {

    private final ChatService chatService;

    @ResponseStatus(HttpStatus.CREATED)
    @MessageMapping("/{roomId}")
    @SendTo("/api/chats/{roomId}")
    public CommonResponse<ChatMessage> createChat(@DestinationVariable Long roomId, ChatMessage message) {
        Chat chat = chatService.createChat(roomId, message.getSender(), message.getMessage());
        ChatMessage chatMessage = message.createChat(roomId, chat.getSender(), chat.getMessage(), chat.getCreatedAt());
        return ApiUtils.success(201, chatMessage);
    }

    @ApiOperation(value = "채팅 내역 조회", notes = "채팅방의 채팅 내역을 조회합니다.")
    @GetMapping("/api/chats/{roomId}")
    public @ResponseBody CommonResponse<List<ChatResponseDto>> joinRoom(@PathVariable Long roomId) {
        return ApiUtils.success(201, chatService.findAllChatByRoomId(roomId));
    }

}
