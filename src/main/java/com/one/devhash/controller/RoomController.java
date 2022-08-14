package com.one.devhash.controller;

import com.one.devhash.domain.Chat;
import com.one.devhash.domain.Room;
import com.one.devhash.global.response.ApiUtils;
import com.one.devhash.global.response.CommonResponse;
import com.one.devhash.service.ChatService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/chats")
public class RoomController {

    private final ChatService chatService;

    @GetMapping("/{roomId}")
    public CommonResponse<List<Chat>> joinRoom(@PathVariable Long roomId) {
        List<Chat> chatList = chatService.findAllChatByRoomId(roomId);
        return ApiUtils.success(201, chatList);
    }

    @PostMapping("/{productId}")
    public CommonResponse<?> createRoom(@PathVariable Long productId) {
        chatService.createRoom(productId);
        return ApiUtils.success(201, null);
    }

    // TODO: 2022/08/14 유저 기능 개발 후 각 유저의 전체 채팅방 조회 기능 개발 예정
    @GetMapping()
    public CommonResponse<List<Room>> getRoomList() {
        return ApiUtils.success(200, null);
    }

}
