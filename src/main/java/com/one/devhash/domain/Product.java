package com.one.devhash.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@Entity
@NoArgsConstructor
public class Product extends Timestamped {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column
	private Long productId;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id")
	private User user;
//	private Image image; 이미지

	@Column
	private String productTitle;
	@Column
	private String productContent;
	@Column
	private int productPrice;

	@Builder
	public Product(User user, String productTitle, String productContent, int productPrice) { //, User user
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
