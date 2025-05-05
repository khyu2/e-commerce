package project.ecommerce.api.user.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import project.ecommerce.common.exception.ExceptionType;

@Getter
@AllArgsConstructor
public enum UserExceptionType implements ExceptionType {

    USER_NOT_FOUND(HttpStatus.NOT_FOUND, "user-001", "사용자를 찾을 수 없습니다."),
    USER_ALREADY_EXISTS(HttpStatus.BAD_REQUEST, "user-002", "이미 존재하는 사용자입니다."),
    INVALID_PASSWORD(HttpStatus.BAD_REQUEST, "user-003", "잘못된 비밀번호입니다."),
    INVALID_EMAIL(HttpStatus.BAD_REQUEST, "user-004", "잘못된 이메일입니다."),
    INVALID_PROFILE_IMAGE_URL(HttpStatus.BAD_REQUEST, "user-005", "잘못된 프로필 이미지 URL입니다."),

    INVALID_ADDRESS(HttpStatus.BAD_REQUEST, "address-001", "잘못된 주소입니다."),
    ADDRESS_LIMIT_EXCEEDED(HttpStatus.BAD_REQUEST, "address-002", "최대 주소 허용량을 초과했습니다."),
    ADDRESS_UNDERFLOW(HttpStatus.BAD_REQUEST, "address-003", "최소 하나의 주소는 필요합니다."),
    DEFAULT_ADDRESS_NOT_FOUND(HttpStatus.NOT_FOUND, "address-004", "주소를 찾을 수 없습니다."),

    UNAUTHORIZED(HttpStatus.UNAUTHORIZED, "auth-001", "인증되지 않은 사용자입니다."),
    FORBIDDEN(HttpStatus.FORBIDDEN, "auth-002", "접근 권한이 없습니다."),
    ;

    private final HttpStatus httpStatus;
    private final String errorCode;
    private final String message;
}
