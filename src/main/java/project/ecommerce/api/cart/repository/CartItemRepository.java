package project.ecommerce.api.cart.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import project.ecommerce.api.cart.entity.CartItem;
import project.ecommerce.api.product.entity.Product;
import project.ecommerce.api.user.entity.User;

import java.util.List;
import java.util.Optional;

public interface CartItemRepository extends JpaRepository<CartItem, Long> {

    List<CartItem> findAllByUserEmail(String email);

    Optional<CartItem> findByUserAndProduct(User user, Product product);
}
