package project.ecommerce.api.order.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;
import project.ecommerce.api.user.entity.User;
import project.ecommerce.common.utils.BaseTimeEntity;

@Entity
@Getter
@SQLDelete(sql = "UPDATE tb_order SET deleted_at = now() WHERE id = ?")
@SQLRestriction("deleted_at IS NULL")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@Table(name = "tb_order")
public class Order extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long totalPrice;

    @Enumerated(EnumType.STRING)
    private OrderStatus status;

    // TODO: change Column
    private String paymentMethod;

    // TODO: change Column
    private String paymentStatus;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    public void updateStatus(OrderStatus status) {
        this.status = status;
    }
}
