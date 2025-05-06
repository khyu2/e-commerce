package project.ecommerce.api.wishlist.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import project.ecommerce.common.exception.ExceptionType;

@Getter
@AllArgsConstructor
public enum WishlistItemException implements ExceptionType {

    WISHLIST_ITEM_NOT_FOUND(HttpStatus.NOT_FOUND, "WISHLIST_ITEM_NOT_FOUND", "위시리스트 아이템을 찾을 수 없습니다."),
    UNAUTHORIZED(HttpStatus.UNAUTHORIZED, "UNAUTHORIZED", "위시리스트 아이템에 대한 권한이 없습니다."),
    ;

    private final HttpStatus httpStatus;
    private final String errorCode;
    private final String message;
}
