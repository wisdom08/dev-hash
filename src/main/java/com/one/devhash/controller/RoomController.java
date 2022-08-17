package com.one.devhash.controller;

import com.one.devhash.dto.chatting.RoomResponseDto;
import com.one.devhash.global.response.ApiUtils;
import com.one.devhash.global.response.CommonResponse;
import com.one.devhash.service.RoomService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/chats")
public class RoomController {

    private final RoomService roomService;

    @ApiOperation(value = "채팅방 생성")
    @PostMapping("/{productId}")
    public CommonResponse<?> createRoom(@PathVariable Long productId) {
        roomService.createRoom(productId);
        return ApiUtils.success(201, null);
    }

    @ApiOperation(value = "모든 채팅방 조회", notes = "내 채팅방 목록을 조회합니다.")
    @GetMapping()
    public CommonResponse<List<RoomResponseDto>> getRoomList() {
        return ApiUtils.success(200, roomService.getRoomList());
    }

    @ApiOperation(value = "채팅방 삭제")
    @DeleteMapping("/{roomId}")
    public CommonResponse<?> deleteRoom(@PathVariable Long roomId) {
        roomService.deleteRoom(roomId);
        return ApiUtils.success(200, null);
    }
}
