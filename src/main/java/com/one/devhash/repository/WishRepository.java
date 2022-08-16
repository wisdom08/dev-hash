package com.one.devhash.repository;


import com.one.devhash.domain.Wish;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface WishRepository extends JpaRepository<Wish, Long> {
	Optional<Wish> findByUserIdAndProductProductId(Long userId, Long productId);
	List<Wish> findAllByUserId(Long userId);
	int countByProductProductId(Long productId);
}