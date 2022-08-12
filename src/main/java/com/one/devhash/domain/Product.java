package com.one.devhash.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Getter
@Entity
@NoArgsConstructor
public class Product extends Timestamped {
	@Id
	@GeneratedValue
	@Column
	private Long productId;

	@JsonBackReference
	private User user;
//	private Image image; 이미지

	private String productTitle;
	private String productContent;
	private int productPrice;

	@Builder
	public Product(User user, String productTitle, String productContent, int productPrice) {
		this.user = user;
		this.productTitle = productTitle;
		this.productContent = productContent;
		this.productPrice = productPrice;
	}

	public void update(String productTitle, String productContent, int productPrice) {
		this.productTitle = productTitle;
		this.productContent = productContent;
		this.productPrice = productPrice;
	}
}