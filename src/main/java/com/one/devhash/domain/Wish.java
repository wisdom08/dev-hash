package com.one.devhash.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;

@Getter
@Entity
@NoArgsConstructor
public class Wish {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column
	private Long wishId;

	@ManyToOne
	@OnDelete(action = OnDeleteAction.CASCADE)
	@JoinColumn(name = "user_id")
	private User user;
	@ManyToOne
	@OnDelete(action = OnDeleteAction.CASCADE)
	@JoinColumn(name = "product_id")
	private Product product;

	@Builder
	public Wish(User user, Product product) {
		this.user = user;
		this.product = product;
	}
}
