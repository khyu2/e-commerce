package project.ecommerce.api.cart.dto.response;

import project.ecommerce.api.cart.entity.CartItem;
import project.ecommerce.api.product.dto.response.ProductCategoryResponse;
import project.ecommerce.api.product.entity.Product;

public record CartItemResponse(
        Long id,
        Long productId,
        String productName,
        Long productPrice,
        String productImageUrl,
        long quantity,
        ProductCategoryResponse productCategory
) {
    public static CartItemResponse of(CartItem cartItem) {
        Product product = cartItem.getProduct();
        return new CartItemResponse(
                cartItem.getId(),
                product.getId(),
                product.getName(),
                product.getPrice(),
                null, // TODO: ProductImage Response 생성 후 넣기
                cartItem.getQuantity(),
                ProductCategoryResponse.of(product.getCategory())
        );
    }
}
