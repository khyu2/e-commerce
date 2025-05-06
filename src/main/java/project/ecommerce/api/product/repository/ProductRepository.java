package project.ecommerce.api.product.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import project.ecommerce.api.product.entity.Product;

public interface ProductRepository extends JpaRepository<Product, Long> {
}
