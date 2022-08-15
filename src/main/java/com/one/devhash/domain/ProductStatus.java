package com.one.devhash.domain;

import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum ProductStatus {
	FOR_SALE("판매중"),
	RESERVED("예약중"),
	PAY("결제완료"),
	CANCEL("거래취소"),
	SOLD_OUT("판매완료")
	;

	private String value;

	@JsonValue
	public String getValue() {
		return value;
	}
}