package com.one.devhash.domain;

import lombok.*;

import javax.persistence.*;

@Entity
@Getter
public class Room {
    @Id
    @GeneratedValue
    @Column(name = "room_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    private Product product;

    protected Room() {};

    private Room(Product product) {
        this.product = product;
    }

    public static Room createRoom(Product product) {
        return new Room(product);
    }

}
