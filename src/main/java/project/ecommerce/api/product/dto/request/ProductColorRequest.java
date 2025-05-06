package project.ecommerce.api.product.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record ProductColorRequest(
        @NotBlank(message = "색상은 필수입니다.")
        @Size(max = 30, message = "색상은 30자를 초과할 수 없습니다.") String name,
        String colorCode
) {
}
