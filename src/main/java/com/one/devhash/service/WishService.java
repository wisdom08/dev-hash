package com.one.devhash.service;

import com.one.devhash.domain.Product;
import com.one.devhash.domain.User;
import com.one.devhash.domain.Wish;
import com.one.devhash.global.error.exception.EntityNotFoundException;
import com.one.devhash.global.error.exception.ErrorCode;
import com.one.devhash.repository.ProductRepository;
import com.one.devhash.repository.WishRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class WishService {
	private final ProductRepository productRepository;
	private final WishRepository wishRepository;
	private final UserService userService;

	@Transactional
	public void isWish(Long productId) {
		Product product = productRepository.findByProductId(productId)
				.orElseThrow(() -> new EntityNotFoundException(ErrorCode.NOTFOUND_PRODUCT));
		User user = userService.findByUserName(getCurrentUsername());
		if(user == product.getUser()) { throw new EntityNotFoundException(ErrorCode.WISH_ME); }
		Optional<Wish> wish = wishRepository.findByUserIdAndProductProductId(user.getId(), productId);
		if(wish.isEmpty()) {
			wishRepository.save(new Wish(user, product));
		}else { wishRepository.delete(wish.orElseThrow(() -> new EntityNotFoundException(ErrorCode.WISH_FAILED)));}
	}

	private static String getCurrentUsername() {
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		return ((UserDetails)principal).getUsername();
	}
}