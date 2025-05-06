package project.ecommerce.api.cart.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import project.ecommerce.common.exception.ExceptionType;

@Getter
@AllArgsConstructor
public enum CartItemException implements ExceptionType {

    CART_ITEM_NOT_FOUND(HttpStatus.NOT_FOUND, "CART-001", "장바구니 아이템을 찾을 수 없습니다."),
    INVALID_QUANTITY(HttpStatus.BAD_REQUEST, "CART-002", "장바구니 아이템의 수량은 0보다 커야 합니다."),
    ;

    private final HttpStatus httpStatus;
    private final String errorCode;
    private final String message;
}
