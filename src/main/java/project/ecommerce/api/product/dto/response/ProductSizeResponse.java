package project.ecommerce.api.product.dto.response;

import project.ecommerce.api.product.entity.ProductSize;

public record ProductSizeResponse(
        Long id,
        String name
) {
    public static ProductSizeResponse of(ProductSize productSize) {
        return new ProductSizeResponse(
                productSize.getId(),
                productSize.getName()
        );
    }
}
