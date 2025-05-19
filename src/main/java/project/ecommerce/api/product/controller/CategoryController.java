package project.ecommerce.api.product.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import project.ecommerce.api.product.dto.response.ProductCategoryResponse;
import project.ecommerce.api.product.service.ProductCategoryService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/categories")
public class CategoryController {

    private final ProductCategoryService productCategoryService;

    // 카테고리 목록 조회 API
    @GetMapping("/")
    public ResponseEntity<List<ProductCategoryResponse>> getCategories() {
        return ResponseEntity.ok(productCategoryService.getCategories());
    }

    // 카테고리 상세 조회 API
    @GetMapping("/{categoryId}")
    public ResponseEntity<ProductCategoryResponse> getCategoryDetail(@PathVariable Long categoryId) {
        return ResponseEntity.ok(productCategoryService.getCategoryDetail(categoryId));
    }
}
