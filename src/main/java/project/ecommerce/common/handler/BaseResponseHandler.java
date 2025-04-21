package project.ecommerce.common.handler;

import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpResponse;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;
import project.ecommerce.common.response.BaseResponse;

import java.util.List;

@Slf4j
@RestControllerAdvice
@RequiredArgsConstructor
public class BaseResponseHandler implements ResponseBodyAdvice<Object> {

    private static final List<String> NO_CHECK_URLS = List.of(
            "/api/v3/api-docs",
            "/api/actuator/prometheus"
    );

    @Override
    public boolean supports(MethodParameter returnType,
                            Class<? extends HttpMessageConverter<?>> converterType
    ) {
        return true;
    }

    @Override
    public Object beforeBodyWrite(Object body,
                                  MethodParameter returnType,
                                  MediaType selectedContentType,
                                  Class<? extends HttpMessageConverter<?>> selectedConverterType,
                                  ServerHttpRequest request,
                                  ServerHttpResponse response
    ) {
        HttpServletResponse servletResponse = ((ServletServerHttpResponse) response).getServletResponse();

        int status = servletResponse.getStatus();
        HttpStatus resolve = HttpStatus.resolve(status);

        if (resolve == null) {
            return body;
        }

        var requestURI = request.getURI().getPath();
        if (NO_CHECK_URLS.stream().anyMatch(requestURI::startsWith)) {
            return body;
        }

        if (resolve.is2xxSuccessful()) {
            if (body instanceof BaseResponse) {
                return body;
            }
            return new BaseResponse<>(body);
        }

        return body;
    }
}