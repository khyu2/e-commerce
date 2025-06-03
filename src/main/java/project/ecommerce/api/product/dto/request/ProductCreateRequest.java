package project.ecommerce.api.product.dto.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record ProductCreateRequest(
    @NotBlank(message = "상품 이름은 필수입니다")
    @Size(max = 100, message = "상품 이름은 100자를 초과할 수 없습니다")
    String name,

    @Size(max = 1000, message = "설명은 1000자를 초과할 수 없습니다")
    String description,

    @NotNull(message = "가격은 필수입니다")
    @Min(value = 0, message = "가격은 0 이상이어야 합니다")
    Long price,

    @NotNull(message = "카테고리는 필수입니다")
    Long categoryId
) {
}
