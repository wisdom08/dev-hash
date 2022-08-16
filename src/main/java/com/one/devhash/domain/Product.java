package com.one.devhash.domain;

import com.one.devhash.dto.product.ProductRequestDto;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@Entity
@NoArgsConstructor
public class Product extends Timestamped {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column
	private Long productId;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id")
	private User user;
	@Column
	private String productTitle;
	@Column
	private String productContent;
	@Column
	private int productPrice;
	@Column
	@Enumerated(EnumType.STRING)
	private ProductStatus productStatus;

	@Builder
	public Product(User user, String productTitle, String productContent, int productPrice, ProductStatus productStatus) {
		this.user = user;
		this.productTitle = productTitle;
		this.productContent = productContent;
		this.productPrice = productPrice;
		this.productStatus = productStatus;
	}

	public void update(ProductRequestDto requestDto) {
		this.productTitle = requestDto.getProductTitle();
		this.productContent = requestDto.getProductContent();
		this.productPrice = requestDto.getProductPrice();
		this.productStatus = requestDto.getProductStatus();
	}

	public void updateStatus(ProductStatus productStatus) {
		this.productStatus = productStatus;
	}
}