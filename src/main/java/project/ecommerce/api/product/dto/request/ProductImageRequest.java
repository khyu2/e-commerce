package project.ecommerce.api.product.dto.request;

import jakarta.validation.constraints.NotBlank;

public record ProductImageRequest(
        @NotBlank(message = "이미지 URL은 필수입니다")
//        @Pattern(regexp = "^(https?|ftp)://[-a-zA-Z0-9+&@#/%?=~_|!:,.;]*[-a-zA-Z0-9+&@#/%=~_|]$",
//                message = "유효하지 않은 URL 형식입니다")
        String imageUrl
) {
}
