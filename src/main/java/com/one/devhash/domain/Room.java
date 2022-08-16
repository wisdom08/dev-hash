package com.one.devhash.domain;

import com.one.devhash.utils.AuditingFields;
import lombok.Getter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
public class Room extends AuditingFields {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "room_id")
    private Long id;

    @ManyToOne (fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    private Product product;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(mappedBy = "room", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private final List<Chat> chat = new ArrayList<>();

    public void setUser(User user) {
        this.user = user;
    }

    protected Room() {};

    private Room(Product product, User user) {
        this.product = product;
        this.user = user;
    }

    public static Room createRoom(Product product, User user) {
        return new Room(product, user);
    }
}
