package com.one.devhash.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import javax.persistence.*;

@RequiredArgsConstructor
@Getter
@Entity
public class RefreshToken {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "token_id")
    private Long id;

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;
    private String token;

    public void setUser(User user) {
        this.user = user;
    }

    @Builder
    private RefreshToken(User user, String token) {
        this.user = user;
        this.token = token;
    }

    static public RefreshToken createToken(User user, String token){
        return new RefreshToken(user, token);
    }
}
