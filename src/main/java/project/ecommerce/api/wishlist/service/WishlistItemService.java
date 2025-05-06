package project.ecommerce.api.wishlist.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import project.ecommerce.api.product.entity.Product;
import project.ecommerce.api.product.exception.ProductException;
import project.ecommerce.api.product.repository.ProductRepository;
import project.ecommerce.api.user.entity.User;
import project.ecommerce.api.user.exception.UserExceptionType;
import project.ecommerce.api.user.repository.UserRepository;
import project.ecommerce.api.wishlist.dto.response.WishlistItemResponse;
import project.ecommerce.api.wishlist.entity.WishlistItem;
import project.ecommerce.api.wishlist.exception.WishlistItemException;
import project.ecommerce.api.wishlist.repository.WishlistItemRepository;
import project.ecommerce.common.exception.BaseException;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class WishlistItemService {

    private final UserRepository userRepository;
    private final ProductRepository productRepository;
    private final WishlistItemRepository wishlistItemRepository;

    public List<WishlistItemResponse> getWishlistItems(String username) {
        return wishlistItemRepository.findAllByUserEmail(username).stream()
                .map(WishlistItemResponse::of)
                .toList();
    }

    public List<WishlistItemResponse> addWishlistItem(String username, Long productId) {
        User user = userRepository.findByEmail(username)
                .orElseThrow(() -> new BaseException(UserExceptionType.USER_NOT_FOUND));
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new BaseException(ProductException.PRODUCT_NOT_FOUND));

        if (wishlistItemRepository.existsByUserAndProduct(user, product)) {
            throw new BaseException(WishlistItemException.WISHLIST_ITEM_NOT_FOUND);
        }

        wishlistItemRepository.save(WishlistItem.builder()
                .user(user)
                .product(product)
                .build());

        return wishlistItemRepository.findAllByUserEmail(username).stream()
                .map(WishlistItemResponse::of)
                .toList();
    }

    public List<WishlistItemResponse> deleteWishlistItem(String username, Long wishlistItemId) {
        User user = userRepository.findByEmail(username)
                .orElseThrow(() -> new BaseException(UserExceptionType.USER_NOT_FOUND));
        WishlistItem wishlistItem = wishlistItemRepository.findById(wishlistItemId)
                .orElseThrow(() -> new BaseException(WishlistItemException.WISHLIST_ITEM_NOT_FOUND));

        if (!wishlistItem.getUser().getId().equals(user.getId())) {
            throw new BaseException(WishlistItemException.UNAUTHORIZED);
        }

        wishlistItemRepository.delete(wishlistItem);

        return wishlistItemRepository.findAllByUserEmail(username).stream()
                .map(WishlistItemResponse::of)
                .toList();
    }
}
