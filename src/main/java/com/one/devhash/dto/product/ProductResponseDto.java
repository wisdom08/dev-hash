package com.one.devhash.dto.product;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.one.devhash.domain.Imagefile;
import com.one.devhash.domain.Product;
import com.one.devhash.domain.ProductStatus;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
public class ProductResponseDto {
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private final String modifiedAt;
	private Long productId;
	private String productTitle;
	private String productcontent;
	private int productPrice;
	private ProductStatus productStatus;
	private Object[] imagefiles;

	@Builder
	public ProductResponseDto(Product product, List<Imagefile> imagefiles) {
		this.modifiedAt = product.getModifiedAt();
		this.productId = product.getProductId();
		this.productTitle = product.getProductTitle();
		this.productcontent = product.getProductContent();
		this.productPrice = product.getProductPrice();
		this.productStatus = product.getProductStatus();
		this.imagefiles = imagefiles.stream()
				.map(Imagefile::getImageUrl)
				.toArray();
	}
}
