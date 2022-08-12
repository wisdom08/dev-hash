package com.one.devhash.repository;

import com.one.devhash.domain.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.*;

public interface ProductRepository extends JpaRepository<Product, Long> {
	List<Product> findAllByOrderByCreatedAtAsc();
	Optional<Product> findByProductId(Long Id);
}