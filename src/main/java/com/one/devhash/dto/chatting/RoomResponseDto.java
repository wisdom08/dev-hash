package com.one.devhash.dto.chatting;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Data
@NoArgsConstructor
public class RoomResponseDto {
    private Long roodId;
    private String productTitle;
    private String me;
    private String sender;

    public RoomResponseDto(Long roodId, String productTitle, String me, String sender) {
        this.roodId = roodId;
        this.productTitle = productTitle;
        this.me = me;
        this.sender = sender;
    }
}

