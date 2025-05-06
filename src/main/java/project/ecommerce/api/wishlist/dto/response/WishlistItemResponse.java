package project.ecommerce.api.wishlist.dto.response;

import project.ecommerce.api.product.dto.response.ProductResponse;
import project.ecommerce.api.wishlist.entity.WishlistItem;

public record WishlistItemResponse(
        Long id,
        ProductResponse product
) {
    public static WishlistItemResponse of(WishlistItem wishlistItem) {
        return new WishlistItemResponse(
                wishlistItem.getId(),
                ProductResponse.of(wishlistItem.getProduct())
        );
    }
}
