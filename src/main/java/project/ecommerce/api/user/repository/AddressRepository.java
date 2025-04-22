package project.ecommerce.api.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import project.ecommerce.api.user.entity.Address;

public interface AddressRepository extends JpaRepository<Address, Long> {
}
