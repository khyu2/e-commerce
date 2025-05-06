package project.ecommerce.api.product.dto.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

public record ProductCategoryRequest(
        @NotBlank(message = "카테고리 ID는 필수입니다.")
        @Min(value = 1, message = "카테고리 ID는 1 이상이어야 합니다.")
        Long categoryId
) {
}
