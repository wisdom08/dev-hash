package com.one.devhash.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@Entity
@NoArgsConstructor
public class Imagefile {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column
	private Long imageId;

	@Column
	@Enumerated(EnumType.STRING)
	private ImageTarget imageTarget;
	@Column
	private String imageUrl;

	@Column
	private Long targetId;

	@Builder
	public Imagefile(ImageTarget imageTarget, Long targetId, String imageUrl) {
		this.imageTarget = imageTarget;
		this.targetId = targetId;
		this.imageUrl = imageUrl;
	}
}