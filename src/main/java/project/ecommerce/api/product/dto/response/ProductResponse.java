package project.ecommerce.api.product.dto.response;

import project.ecommerce.api.product.entity.Product;

import java.util.List;

public record ProductResponse(
        Long id,
        String name,
        Long price,
        String imageUrl,
        String description,
        ProductCategoryResponse productCategory,
        List<ProductColorResponse> productColor,
        List<ProductImageResponse> productImage,
        List<ProductSizeResponse> productSize
) {
    public static ProductResponse of(Product product) {
        return new ProductResponse(
                product.getId(),
                product.getName(),
                product.getPrice(),
                null, // TODO: ProductImage Response 생성 후 넣기
                product.getDescription(),
                ProductCategoryResponse.of(product.getCategory()),
                product.getColors().stream()
                        .map(ProductColorResponse::of)
                        .toList(),
                product.getImages().stream()
                        .map(ProductImageResponse::of)
                        .toList(),
                product.getSizes().stream()
                        .map(ProductSizeResponse::of)
                        .toList()
        );
    }
}
