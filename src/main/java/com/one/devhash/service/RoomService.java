package com.one.devhash.service;

import com.one.devhash.domain.Product;
import com.one.devhash.domain.Room;
import com.one.devhash.domain.User;
import com.one.devhash.dto.chatting.RoomResponseDto;
import com.one.devhash.global.error.exception.ErrorCode;
import com.one.devhash.global.error.exception.InvalidValueException;
import com.one.devhash.repository.RoomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RoomService {
    private final RoomRepository roomRepository;
    private final ProductService productService;
    private final UserService userService;

    public void createRoom(Long productId) {
        Product product = productService.getProduct(productId);
        User user = userService.findByUserName(getCurrentUsername());

        if (product.getUser().getId().equals(user.getId())) {
            throw new InvalidValueException(ErrorCode.INVALID_REQUEST_FOR_CHAT);
        }

        if (roomRepository.findByProductAndUser(product, user).isPresent()) {
            throw new InvalidValueException(ErrorCode.DUPLICATED_REQUEST_FOR_CHAT);
        }

        roomRepository.save(Room.createRoom(product, user));
    }

    @Transactional
    public List<RoomResponseDto> getRoomList() {
        User user = userService.findByUserName(getCurrentUsername());
        List<Room> rooms = user.getRooms();
        return rooms.stream().map(v -> new RoomResponseDto(v.getId(), v.getProduct().getProductTitle(), v.getUser().getUserName())).collect(Collectors.toList());
    }


    private static String getCurrentUsername() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return ((UserDetails) principal).getUsername();
    }

    public void deleteRoom(Long roomId) {
        exists(roomId);
        roomRepository.deleteById(roomId);
    }

    private void exists(Long roomId) {
        roomRepository.findById(roomId).orElseThrow(() -> new InvalidValueException(ErrorCode.NOTFOUND_CATTING_ROOM));
    }
}
