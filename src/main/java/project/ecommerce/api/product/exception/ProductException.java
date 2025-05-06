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
    PRODUCT_IMAGE_NOT_FOUND(HttpStatus.NOT_FOUND, "PRODUCT-003", "상품 이미지를 찾을 수 없습니다."),
    PRODUCT_COLOR_NOT_FOUND(HttpStatus.NOT_FOUND, "PRODUCT-004", "상품 색상을 찾을 수 없습니다."),
    PRODUCT_SIZE_NOT_FOUND(HttpStatus.NOT_FOUND, "PRODUCT-005", "상품 사이즈를 찾을 수 없습니다."),
    PRODUCT_FEATURE_NOT_FOUND(HttpStatus.NOT_FOUND, "PRODUCT-006", "상품 기능을 찾을 수 없습니다."),

    PRODUCT_ALREADY_EXISTS(HttpStatus.CONFLICT, "PRODUCT-007", "이미 존재하는 상품입니다."),
    ;

    private final HttpStatus httpStatus;
    private final String errorCode;
    private final String message;
}
