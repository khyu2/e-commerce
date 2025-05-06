package project.ecommerce.api.cart.controller;

import io.swagger.v3.oas.annotations.Parameter;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import project.ecommerce.api.cart.dto.request.CartItemCreateRequest;
import project.ecommerce.api.cart.dto.response.CartItemResponse;
import project.ecommerce.api.cart.service.CartItemService;
import project.ecommerce.common.auth.resolver.Auth;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/cart-items")
public class CartItemController {

    private final CartItemService cartItemService;

    // 장바구니 상품 추가 API
    @PostMapping("/")
    public ResponseEntity<List<CartItemResponse>> addCartItem(
            @Parameter(hidden = true) @Auth String username,
            @Valid @RequestBody CartItemCreateRequest request
    ) {
        return ResponseEntity.ok(cartItemService.addCartItem(username, request));
    }

    // 장바구니 상품 목록 조회 API
    @GetMapping("/")
    public ResponseEntity<List<CartItemResponse>> getCartItems(@Parameter(hidden = true) @Auth String username) {
        return ResponseEntity.ok(cartItemService.getCartItems(username));
    }

    // 장바구니 상품 수량 변경 API
    @PutMapping("/{cartItemId}")
    public void updateCartItem(@PathVariable Long cartItemId) {
        // 장바구니 상품 수량 변경 로직
    }

    // 장바구니 상품 삭제 API
    @DeleteMapping("/{cartItemId}")
    public void deleteCartItem(@PathVariable Long cartItemId) {
        // 장바구니 상품 삭제 로직
    }
}
