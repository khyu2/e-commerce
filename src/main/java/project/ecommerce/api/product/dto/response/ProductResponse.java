package project.ecommerce.api.product.dto.response;

import project.ecommerce.api.product.entity.Product;

public record ProductResponse(
    Long id,
    String name,
    Long price,
    String imageUrl,
    String description,
    ProductCategoryResponse productCategory
) {
    public static ProductResponse of(Product product) {
        return new ProductResponse(
            product.getId(),
            product.getName(),
            product.getPrice(),
            null, // TODO: ProductImage Response 생성 후 넣기
            product.getDescription(),
            ProductCategoryResponse.of(product.getCategory())
        );
    }
}
