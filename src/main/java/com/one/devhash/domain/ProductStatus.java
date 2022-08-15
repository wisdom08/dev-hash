package com.one.devhash.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public enum ProductStatus {
	FOR_SALE("판매중"),
	RESERVED("예약중"),
	PAY("결제완료"),
	CANCEL("거래취소"),
	SOLD_OUT("판매완료")
	;

	@Getter
	private String des;
}