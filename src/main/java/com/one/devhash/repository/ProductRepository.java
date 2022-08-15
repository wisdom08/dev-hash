package com.one.devhash.repository;

import com.one.devhash.domain.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, Long> {
	Page<Product> findAllByOrderByCreatedAtAsc(Pageable pageable);
	Optional<Product> findByProductId(Long Id);
}