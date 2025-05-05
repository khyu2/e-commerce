package project.ecommerce.api.order.entity;

public enum OrderStatus {

    PENDING("주문 대기"),
    PAYMENT_COMPLETED("결제 완료"),
    SHIPPING("배송 중"),
    DELIVERED("배송 완료"),
    CANCELED("주문 취소");

    private final String description;

    OrderStatus(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
