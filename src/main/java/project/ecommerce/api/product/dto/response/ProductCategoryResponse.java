package project.ecommerce.api.product.dto.response;

import project.ecommerce.api.product.entity.ProductCategory;

public record ProductCategoryResponse(
        Long id,
        String name,
        Boolean isActive
) {
    public static ProductCategoryResponse of(ProductCategory productCategory) {
        return new ProductCategoryResponse(
                productCategory.getId(),
                productCategory.getName(),
                productCategory.getIsActive()
        );
    }
}
