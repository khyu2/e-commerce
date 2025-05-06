package project.ecommerce.api.product.dto.response;

import project.ecommerce.api.product.entity.ProductCategory;

public record ProductCategoryResponse(
        Long id,
        String name
) {
    public static ProductCategoryResponse of(ProductCategory productCategory) {
        return new ProductCategoryResponse(
                productCategory.getId(),
                productCategory.getName()
        );
    }
}
