package project.ecommerce.api.product.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import project.ecommerce.api.product.entity.ProductCategory;

public interface ProductCategoryRepository extends JpaRepository<ProductCategory, Long> {
}
