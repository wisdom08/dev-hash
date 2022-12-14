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
	private String userName;
	private Long productId;
	private String productTitle;
	private String productContent;
	private int productPrice;
	private ProductStatus productStatus;
	private int wishCount;
	private Object[] imagefiles;

	@Builder
	public ProductResponseDto(Product product, List<Imagefile> imagefiles, int wishCount) {
		this.modifiedAt = product.getModifiedAt();
		this.userName = product.getUser().getUserName();
		this.productId = product.getProductId();
		this.productTitle = product.getProductTitle();
		this.productContent = product.getProductContent();
		this.productPrice = product.getProductPrice();
		this.productStatus = product.getProductStatus();
		this.wishCount = wishCount;
		this.imagefiles = imagefiles.stream()
				.map(Imagefile::getImageUrl)
				.toArray();
	}
}
