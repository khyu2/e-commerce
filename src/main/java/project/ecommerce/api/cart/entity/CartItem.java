package project.ecommerce.api.cart.entity;

import jakarta.persistence.*;
import lombok.*;
import project.ecommerce.api.cart.exception.CartItemException;
import project.ecommerce.api.product.entity.Product;
import project.ecommerce.api.user.entity.User;
import project.ecommerce.common.exception.BaseException;
import project.ecommerce.common.utils.BaseTimeEntity;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@Table(name = "tb_cart_item")
public class CartItem extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long quantity;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    public void updateQuantity(Long quantity) {
        if (quantity <= 0) {
            throw new BaseException(CartItemException.INVALID_QUANTITY);
        }
        this.quantity = quantity;
    }
}
