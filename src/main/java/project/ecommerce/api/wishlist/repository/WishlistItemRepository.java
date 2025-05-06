package project.ecommerce.api.wishlist.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import project.ecommerce.api.product.entity.Product;
import project.ecommerce.api.user.entity.User;
import project.ecommerce.api.wishlist.entity.WishlistItem;

import java.util.List;

public interface WishlistItemRepository extends JpaRepository<WishlistItem, Long> {

    List<WishlistItem> findAllByUserEmail(String email);

    boolean existsByUserAndProduct(User user, Product product);
}
