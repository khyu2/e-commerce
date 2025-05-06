package project.ecommerce.api.product.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import project.ecommerce.api.product.entity.ProductFeature;

public interface ProductFeatureRepository extends JpaRepository<ProductFeature, Long> {
}
