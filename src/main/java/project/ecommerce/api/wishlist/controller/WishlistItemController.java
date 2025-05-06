package project.ecommerce.api.wishlist.controller;

import io.swagger.v3.oas.annotations.Parameter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import project.ecommerce.api.wishlist.dto.response.WishlistItemResponse;
import project.ecommerce.api.wishlist.service.WishlistItemService;
import project.ecommerce.common.auth.resolver.Auth;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/wishlist-items")
public class WishlistItemController {

    private final WishlistItemService wishlistItemService;

    @PostMapping("/{productId}")
    public ResponseEntity<List<WishlistItemResponse>> addWishlistItem(
            @Parameter(hidden = true) @Auth String username,
            @PathVariable Long productId
    ) {
        return ResponseEntity.ok(wishlistItemService.addWishlistItem(username, productId));
    }

    @GetMapping("/")
    public ResponseEntity<List<WishlistItemResponse>> getWishlistItems(@Parameter(hidden = true) @Auth String username) {
        return ResponseEntity.ok(wishlistItemService.getWishlistItems(username));
    }

    @DeleteMapping("/{wishlistItemId}")
    public ResponseEntity<List<WishlistItemResponse>> deleteWishlistItem(
            @Parameter(hidden = true) @Auth String username,
            @PathVariable Long wishlistItemId
    ) {
        return ResponseEntity.ok(wishlistItemService.deleteWishlistItem(username, wishlistItemId));
    }
}
