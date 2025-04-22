package project.ecommerce.common.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Getter;

@Getter
@JsonPropertyOrder({"success", "code", "data"})
@JsonIgnoreProperties(ignoreUnknown = true)
public class BaseResponse<T> {

    @JsonProperty("success")
    private final boolean success;

    private final String code;

    @JsonInclude(JsonInclude.Include.NON_NULL) // NULL인 경우 제외
    private final T data;

    public BaseResponse(T data) {
        this.success = true;
        this.code = "200";
        this.data = data;
    }

    @JsonProperty("isSuccess") // "success"가 추가되지 않도록 명시적으로 설정
    public boolean isSuccess() {
        return success;
    }
}