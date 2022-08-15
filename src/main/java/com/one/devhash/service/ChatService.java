package com.one.devhash.service;

import com.one.devhash.domain.Chat;
import com.one.devhash.domain.Room;
import com.one.devhash.dto.chatting.ChatResponseDto;
import com.one.devhash.global.error.exception.ErrorCode;
import com.one.devhash.global.error.exception.InvalidValueException;
import com.one.devhash.repository.ChatRepository;
import com.one.devhash.repository.RoomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class ChatService {

    private final RoomRepository roomRepository;
    private final ChatRepository chatRepository;


    public Chat createChat(Long roomId, String sender, String message) {
        return chatRepository.save(Chat.createChat(exists(roomId), sender, message));
    }

    public List<ChatResponseDto> findAllChatByRoomId(Long roomId) {
        exists(roomId);
        List<Chat> chatList = chatRepository.findAllByRoomId(roomId);
        return chatList.stream().map(v -> ChatResponseDto.createNewDto(v.getId(), v.getMessage(), v.getSender(), v.getCreatedAt())).collect(Collectors.toList());
    }

    private Room exists(Long roomId) {
        return roomRepository.findById(roomId).orElseThrow(() -> new InvalidValueException(ErrorCode.NOTFOUND_CATTING_ROOM));
    }
}
