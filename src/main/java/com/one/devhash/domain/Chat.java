package com.one.devhash.domain;

import com.one.devhash.utils.AuditingFields;
import lombok.Getter;
import lombok.ToString;

import javax.persistence.*;

@Entity
@Getter
@ToString
public class Chat extends AuditingFields {

    @Id @GeneratedValue
    @Column(name = "chat_id")
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "room_id")
    private Room room;

    private String sender;

    private String message;

    protected Chat() {}

    private Chat(Room room, String sender, String message) {
        this.room = room;
        this.sender = sender;
        this.message = message;
    }

    public static Chat createChat(Room room, String sender, String message) {
        return new Chat(room, sender, message);
    }

}
