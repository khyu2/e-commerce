package project.ecommerce.api.product.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.ecommerce.api.product.dto.request.ProductCreateRequest;
import project.ecommerce.api.product.dto.response.ProductResponse;
import project.ecommerce.api.product.entity.*;
import project.ecommerce.api.product.exception.ProductException;
import project.ecommerce.api.product.repository.ProductCategoryRepository;
import project.ecommerce.api.product.repository.ProductRepository;
import project.ecommerce.api.s3.service.S3Service;
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

    public List<ProductResponse> createProduct(ProductCreateRequest request) {
        ProductCategory productCategory = productCategoryRepository.findById(request.categoryId())
                .orElseThrow(() -> new BaseException(ProductException.PRODUCT_CATEGORY_NOT_FOUND));

        Product product = Product.builder()
                .name(request.name())
                .price(request.price())
                .description(request.description())
                .category(productCategory)
                .build();

        productRepository.save(product);

        return productRepository.findAll().stream()
                .map(ProductResponse::of)
                .toList();
    }

    @Transactional(readOnly = true)
    public List<ProductResponse> getProducts() {
        return productRepository.findAll().stream()
                .map(ProductResponse::of)
                .toList();
    }

    @Transactional(readOnly = true)
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
