package project.ecommerce.api.product.dto.response;

import project.ecommerce.api.product.entity.ProductColor;

public record ProductColorResponse(
        Long id,
        String name,
        String colorCode
) {
    public static ProductColorResponse of(ProductColor productColor) {
        return new ProductColorResponse(
                productColor.getId(),
                productColor.getName(),
                productColor.getColorCode()
        );
    }
}
