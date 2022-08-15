package com.one.devhash.dto.chatting;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Data
@NoArgsConstructor
public class RoomResponseDto {
    private Long id;
    private String productTitle;
    private String userName;

    public RoomResponseDto(Long id, String productTitle, String userName) {
        this.id = id;
        this.productTitle = productTitle;
        this.userName = userName;
    }

    public static void of(Long id, String productTitle, String userName) {
        new RoomResponseDto(id, productTitle, userName);
    }
}

