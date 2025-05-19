package project.ecommerce.api.product.controller;

import io.swagger.v3.oas.annotations.Parameter;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import project.ecommerce.api.product.dto.request.ProductCategoryRequest;
import project.ecommerce.api.product.dto.response.ProductCategoryResponse;
import project.ecommerce.api.product.service.ProductCategoryService;
import project.ecommerce.common.auth.resolver.Auth;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/admin/categories")
public class AdminCategoryController {

    private final ProductCategoryService productCategoryService;

    // 카테고리 등록 API
    @PostMapping("/")
    public ResponseEntity<ProductCategoryResponse> addProductCategory(
            @Parameter(hidden = true) @Auth String username,
            @Valid @RequestBody ProductCategoryRequest request
    ) {
        return ResponseEntity.ok(productCategoryService.addProductCategory(request));
    }

    // 카테고리 수정 API

    // 카테고리 활성화 수정 API
    @PutMapping("/toggle/{categoryId}")
    public ResponseEntity<ProductCategoryResponse> toggleProductCategory(
            @Parameter(hidden = true) @Auth String username,
            @PathVariable Long categoryId
    ) {
        return ResponseEntity.ok(productCategoryService.toggleProductCategory(categoryId));
    }

    // 카테고리 삭제 API
}
