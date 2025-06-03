package project.ecommerce.api.product.dto.response;

import project.ecommerce.api.product.entity.ProductCategory;

// TODO: productCount 추가하기
public record ProductCategoryResponse(
        Long id,
        String name,
        String description,
        Boolean isActive
) {
    public static ProductCategoryResponse of(ProductCategory productCategory) {
        return new ProductCategoryResponse(
                productCategory.getId(),
                productCategory.getName(),
                productCategory.getDescription(),
                productCategory.getIsActive()
        );
    }
}
