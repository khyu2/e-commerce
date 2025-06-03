package project.ecommerce.api.product.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import project.ecommerce.api.product.dto.request.ProductCategoryRequest;
import project.ecommerce.api.product.dto.response.ProductCategoryResponse;
import project.ecommerce.api.product.entity.ProductCategory;
import project.ecommerce.api.product.exception.ProductException;
import project.ecommerce.api.product.repository.ProductCategoryRepository;
import project.ecommerce.common.exception.BaseException;

@Service
@Transactional
@RequiredArgsConstructor
public class ProductCategoryService {

    private final ProductCategoryRepository productCategoryRepository;

    public ProductCategoryResponse addProductCategory(ProductCategoryRequest request) {
        if (productCategoryRepository.existsByName(request.name())) {
            throw new BaseException(ProductException.PRODUCT_CATEGORY_ALREADY_EXISTS);
        }

        ProductCategory productCategory = productCategoryRepository.save(ProductCategory.builder()
            .name(request.name())
            .description(request.description())
            .isActive(false)
            .build());

        return ProductCategoryResponse.of(productCategory);
    }

    @Transactional(readOnly = true)
    public List<ProductCategoryResponse> getCategories() {
        return productCategoryRepository.findAll().stream()
            .map(ProductCategoryResponse::of)
            .toList();
    }

    @Transactional(readOnly = true)
    public ProductCategoryResponse getCategoryDetail(Long categoryId) {
        return productCategoryRepository.findById(categoryId)
            .map(ProductCategoryResponse::of)
            .orElseThrow(() -> new BaseException(ProductException.PRODUCT_CATEGORY_NOT_FOUND));
    }

    public ProductCategoryResponse updateProductCategory(Long categoryId, ProductCategoryRequest request) {
        ProductCategory productCategory = productCategoryRepository.findById(categoryId)
            .orElseThrow(() -> new BaseException(ProductException.PRODUCT_CATEGORY_NOT_FOUND));

        productCategory.updateName(request.name());
        productCategory.updateDescription(request.description());

        productCategory.toggleIsActive();

        return ProductCategoryResponse.of(productCategory);
    }

    public ProductCategoryResponse toggleProductCategory(Long categoryId) {
        ProductCategory productCategory = productCategoryRepository.findById(categoryId)
            .orElseThrow(() -> new BaseException(ProductException.PRODUCT_CATEGORY_NOT_FOUND));

        productCategory.toggleIsActive();

        return ProductCategoryResponse.of(productCategory);
    }

    public void deleteProductCategory(Long categoryId) {
        ProductCategory productCategory = productCategoryRepository.findById(categoryId)
            .orElseThrow(() -> new BaseException(ProductException.PRODUCT_CATEGORY_NOT_FOUND));

        productCategoryRepository.delete(productCategory);
    }
}
