package com.one.devhash.service;

import com.one.devhash.domain.Chat;
import com.one.devhash.domain.Product;
import com.one.devhash.domain.Room;
import com.one.devhash.repository.ChatRepository;
import com.one.devhash.repository.RoomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ChatService {
    private final RoomRepository roomRepository;
    private final ChatRepository chatRepository;
    private final ProductService productService;


    public Room createRoom(Long productId) {
        Product product = productService.getProduct(productId);
        return roomRepository.save(Room.createRoom(product));
    }

    public Chat createChat(Long roomId, String sender, String message) {
        Room room = roomRepository.findById(roomId).orElseThrow();
        return chatRepository.save(Chat.createChat(room, sender, message));
    }

    public List<Chat> findAllChatByRoomId(Long roomId) {
        List<Chat> chats = chatRepository.findAllByRoomId(roomId);
        return chatRepository.findAllByRoomId(roomId);
    }

    public void findMyRooms() {
    }
}
