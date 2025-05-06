package project.ecommerce.api.cart.dto.request;

import jakarta.validation.constraints.NotNull;

public record CartItemCreateRequest(
        @NotNull(message = "상품 ID는 공백일 수 없습니다.") Long productId,
        @NotNull(message = "상품 수량은 공백일 수 없습니다.") Long quantity
) {
}
