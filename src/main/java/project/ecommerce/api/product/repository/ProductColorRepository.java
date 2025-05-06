package project.ecommerce.api.product.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import project.ecommerce.api.product.entity.ProductColor;

public interface ProductColorRepository extends JpaRepository<ProductColor, Long> {
}
