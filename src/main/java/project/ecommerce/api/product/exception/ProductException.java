package project.ecommerce.api.product.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import project.ecommerce.common.exception.ExceptionType;

@Getter
@AllArgsConstructor
public enum ProductException implements ExceptionType {

    PRODUCT_NOT_FOUND(HttpStatus.NOT_FOUND, "PRODUCT-001", "상품을 찾을 수 없습니다."),
    PRODUCT_CATEGORY_NOT_FOUND(HttpStatus.NOT_FOUND, "PRODUCT-002", "상품 카테고리를 찾을 수 없습니다."),

    PRODUCT_ALREADY_EXISTS(HttpStatus.CONFLICT, "PRODUCT-007", "이미 존재하는 상품입니다."),
    PRODUCT_CATEGORY_ALREADY_EXISTS(HttpStatus.CONFLICT, "PRODUCT-008", "이미 존재하는 상품 카테고리입니다."),
    ;

    private final HttpStatus httpStatus;
    private final String errorCode;
    private final String message;
}
