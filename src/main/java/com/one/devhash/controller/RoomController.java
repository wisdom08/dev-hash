package com.one.devhash.controller;

import com.one.devhash.dto.chatting.RoomResponseDto;
import com.one.devhash.global.response.ApiUtils;
import com.one.devhash.global.response.CommonResponse;
import com.one.devhash.service.RoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/chats")
public class RoomController {

    private final RoomService roomService;

    @PostMapping("/{productId}")
    public CommonResponse<?> createRoom(@PathVariable Long productId) {
        roomService.createRoom(productId);
        return ApiUtils.success(201, null);
    }

    @GetMapping()
    public CommonResponse<List<RoomResponseDto>> getRoomList() {
        return ApiUtils.success(200, roomService.getRoomList());
    }

    @DeleteMapping("/{roomId}")
    public CommonResponse<?> deleteRoom(@PathVariable Long roomId) {
        roomService.deleteRoom(roomId);
        return ApiUtils.success(200, null);
    }
}
