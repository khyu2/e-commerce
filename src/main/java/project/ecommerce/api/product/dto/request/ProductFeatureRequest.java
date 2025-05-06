package project.ecommerce.api.product.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record ProductFeatureRequest(
        @NotBlank(message = "특징 이름은 필수입니다.")
        @Size(max = 200, message = "특징은 200자를 초과할 수 없습니다.")
        String description
) {
}
