package project.ecommerce.api.product.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import project.ecommerce.api.product.dto.response.ProductResponse;
import project.ecommerce.api.product.service.ProductService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/products")
public class ProductController {

    private final ProductService productService;

    // 상품 목록 조회 API
    @GetMapping("/")
    public ResponseEntity<List<ProductResponse>> getProducts() {
        return ResponseEntity.ok(productService.getProducts());
    }

    // 상품 상세 조회 API
    @GetMapping("/{productId}")
    public ResponseEntity<ProductResponse> getProductDetail(@PathVariable Long productId) {
        return ResponseEntity.ok(productService.getProductDetail(productId));
    }

}
