package project.ecommerce.api.product.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record ProductSizeRequest(
        @NotBlank(message = "사이즈는 필수입니다.")
        @Size(max = 20, message = "사이즈는 20자를 초과할 수 없습니다.")
        String name
) {
}
