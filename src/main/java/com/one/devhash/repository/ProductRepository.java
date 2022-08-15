package com.one.devhash.repository;

import com.one.devhash.domain.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, Long> {
	List<Product> findAllByOrderByCreatedAtAsc();
	Optional<Product> findByProductId(Long Id);
}