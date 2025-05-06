package project.ecommerce.api.product.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import project.ecommerce.api.product.entity.ProductSize;

public interface ProductSizeRepository extends JpaRepository<ProductSize, Long> {
}
