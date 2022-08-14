package com.one.devhash.domain;

import lombok.*;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@ToString
public class Chat {

    @Id @GeneratedValue
    @Column(name = "chat_id")
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "room_id")
    private Room room;

    private String sender;

    @Column(columnDefinition = "TEXT")
    private String message;

    @CreatedDate
    @Column(updatable = false)
    private LocalDateTime createdAt;

    protected Chat() {}

    private Chat(Room room, String sender, String message) {
        this.room = room;
        this.sender = sender;
        this.message = message;
        this.createdAt = LocalDateTime.now();
    }

    public static Chat createChat(Room room, String sender, String message) {
        return new Chat(room, sender, message);
    }

}
