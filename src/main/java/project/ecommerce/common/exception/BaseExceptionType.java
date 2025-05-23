package project.ecommerce.common.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

import static org.springframework.http.HttpStatus.*;

@Getter
@AllArgsConstructor
public enum BaseExceptionType implements ExceptionType {

    UNKNOWN_SERVER_ERROR(INTERNAL_SERVER_ERROR, "500", "서버가 응답할 수 없습니다."),
    ARGUMENT_NOT_VALID(BAD_REQUEST, "401", "요청 인자가 잘못되었습니다."),
    NOT_VALID_METHODS(METHOD_NOT_ALLOWED, "401", "지원하지 않는 메서드입니다."),
    ;

    private final HttpStatus httpStatus;
    private final String errorCode;
    private final String message;

}