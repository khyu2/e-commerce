package project.ecommerce.api.product.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import project.ecommerce.api.product.dto.request.ProductCreateRequest;
import project.ecommerce.api.product.dto.response.ProductResponse;
import project.ecommerce.api.product.entity.*;
import project.ecommerce.api.product.exception.ProductException;
import project.ecommerce.api.product.repository.ProductCategoryRepository;
import project.ecommerce.api.product.repository.ProductRepository;
import project.ecommerce.api.s3.service.S3Service;
import project.ecommerce.api.user.entity.User;
import project.ecommerce.api.user.exception.UserExceptionType;
import project.ecommerce.api.user.repository.UserRepository;
import project.ecommerce.common.exception.BaseException;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class ProductService {

    private final UserRepository userRepository;
    private final ProductRepository productRepository;
    private final ProductCategoryRepository productCategoryRepository;

    private final S3Service s3Service;

    public List<ProductResponse> createProduct(String username, ProductCreateRequest request) {
        User user = userRepository.findByEmail(username)
                .orElseThrow(() -> new BaseException(UserExceptionType.USER_NOT_FOUND));

        ProductCategory productCategory = productCategoryRepository.findById(request.productCategory().categoryId())
                .orElseThrow(() -> new BaseException(ProductException.PRODUCT_CATEGORY_NOT_FOUND));

        List<ProductImage> productImages = request.productImage() != null ?
                request.productImage().stream()
                        .filter(imageRequest -> s3Service.isFileUploaded(imageRequest.imageUrl()))
                        .map(imageRequest -> ProductImage.builder()
                                .imageUrl(imageRequest.imageUrl())
                                .build())
                        .toList() : List.of();

        List<ProductColor> productColors = request.productColor() != null ?
                request.productColor().stream()
                        .map(colorRequest -> ProductColor.builder()
                                .name(colorRequest.name())
                                .colorCode(colorRequest.colorCode())
                                .build())
                        .toList() : List.of();

        List<ProductSize> productSizes = request.productSize() != null ?
                request.productSize().stream()
                        .map(sizeRequest -> ProductSize.builder()
                                .name(sizeRequest.name())
                                .build())
                        .toList() : List.of();

        List<ProductFeature> productFeatures = request.productFeature() != null ?
                request.productFeature().stream()
                        .map(featureRequest -> ProductFeature.builder()
                                .description(featureRequest.description())
                                .build())
                        .toList() : List.of();

        Product product = Product.builder()
                .name(request.name())
                .price(request.price())
                .description(request.description())
                .category(productCategory)
                .images(productImages)
                .colors(productColors)
                .sizes(productSizes)
                .features(productFeatures)
                .build();

        productRepository.save(product);

        return productRepository.findAll().stream()
                .map(ProductResponse::of)
                .toList();
    }

    public List<ProductResponse> getProducts() {
        return productRepository.findAll().stream()
                .map(ProductResponse::of)
                .toList();
    }

    public ProductResponse getProductDetail(Long productId) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new BaseException(ProductException.PRODUCT_NOT_FOUND));

        return ProductResponse.of(product);
    }

    public List<ProductResponse> deleteProduct(Long productId) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new BaseException(ProductException.PRODUCT_NOT_FOUND));

        productRepository.delete(product);

        return productRepository.findAll().stream()
                .map(ProductResponse::of)
                .toList();
    }
}
