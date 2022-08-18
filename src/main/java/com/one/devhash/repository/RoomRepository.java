package com.one.devhash.repository;

import com.one.devhash.domain.Product;
import com.one.devhash.domain.Room;
import com.one.devhash.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface RoomRepository extends JpaRepository<Room, Long> {
    Optional<Room> findByProductAndUser (Product product, User user);
    List<Room> findByProductOrUser (Product product, User user);
}
