package project.ecommerce.api.order.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import project.ecommerce.api.order.service.OrderService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/orders")
public class OrderController {

    private final OrderService orderService;

    // 주문 생성 API
    @PostMapping("/")
    public void createOrder() {
        // 주문 생성 로직
    }

    // 회원 주문 조회 API
    @GetMapping("/")
    public void getOrders() {
        // 회원 주문 조회 로직
    }

    // 주문 상세 조회 API
    @GetMapping("/{orderId}")
    public void getOrderDetail(@PathVariable Long orderId) {
        // 주문 상세 조회 로직
    }

    // 주문 상태 변경 API
    @PutMapping("/status/{orderId}")
    public void updateOrderStatus(@PathVariable Long orderId) {
        // 주문 상태 변경 로직
    }

    // 주문 취소 API
    @DeleteMapping("/{orderId}")
    public void cancelOrder(@PathVariable Long orderId) {
        // 주문 취소 로직
    }
}
