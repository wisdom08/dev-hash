package com.one.devhash.dto.product;

import com.one.devhash.domain.Product;
import com.one.devhash.domain.ProductStatus;
import com.one.devhash.domain.User;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductRequestDto {
	private String productTitle;
	private String productContent;
	private int productPrice;
	private ProductStatus productStatus;

	public Product toProduct(User user) {
		return Product.builder()
				.user(user)
				.productTitle(productTitle)
				.productContent(productContent)
				.productPrice(productPrice)
				.productStatus(productStatus)
				.build();
	}
}