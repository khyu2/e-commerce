package project.ecommerce.api.product.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import project.ecommerce.api.product.entity.ProductImage;

public interface ProductImageRepository extends JpaRepository<ProductImage, Long> {
}
