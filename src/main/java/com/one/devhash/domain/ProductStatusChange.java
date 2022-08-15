package com.one.devhash.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;
import java.util.List;

@AllArgsConstructor
public enum ProductStatusChange {
	FOR_SALE("판매중", Arrays.asList(ProductStatus.PAY, ProductStatus.RESERVED)),

	;

	@Getter
	private String des;
	@Getter
	private List<ProductStatus> possible;
}
