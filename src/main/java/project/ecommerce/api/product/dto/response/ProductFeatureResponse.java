package project.ecommerce.api.product.dto.response;

import project.ecommerce.api.product.entity.ProductFeature;

public record ProductFeatureResponse(
        Long id,
        String description
) {
    public static ProductFeatureResponse of(ProductFeature productFeature) {
        return new ProductFeatureResponse(
                productFeature.getId(),
                productFeature.getDescription()
        );
    }
}
