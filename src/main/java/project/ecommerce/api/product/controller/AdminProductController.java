package project.ecommerce.api.product.controller;

import io.swagger.v3.oas.annotations.Parameter;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import project.ecommerce.api.product.dto.request.ProductCreateRequest;
import project.ecommerce.api.product.dto.response.ProductResponse;
import project.ecommerce.api.product.service.ProductService;
import project.ecommerce.common.auth.resolver.Auth;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/admin/products")
public class AdminProductController {

    private final ProductService productService;

    // 상품 등록 API
    @PostMapping("/")
    public ResponseEntity<List<ProductResponse>> createProduct(
            @Parameter(hidden = true) @Auth String username,
            @Valid @RequestBody ProductCreateRequest request
    ) {
        return ResponseEntity.ok(productService.createProduct(request));
    }

    // 상품 수정 API
    @PutMapping("/{productId}")
    public void updateProduct(@PathVariable Long productId) {
        // 상품 수정 로직
    }

    // 상품 삭제 API
    @DeleteMapping("/{productId}")
    public void deleteProduct(@PathVariable Long productId) {
        // 상품 삭제 로직
    }
}
