package project.ecommerce.api.product.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record ProductCategoryRequest(
        @NotBlank(message = "카테고리 이름은 필수입니다.")
        @Size(max = 20, message = "카테고리 이름은 최대 20자까지 가능합니다.")
        String name,

        Boolean isActive,
        Long categoryId
) {
}
