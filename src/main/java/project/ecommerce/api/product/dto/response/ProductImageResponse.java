package project.ecommerce.api.product.dto.response;

import project.ecommerce.api.product.entity.ProductImage;

public record ProductImageResponse(
        Long id,
        String imageUrl
) {
    public static ProductImageResponse of(ProductImage productImage) {
        return new ProductImageResponse(
                productImage.getId(),
                productImage.getImageUrl()
        );
    }
}
