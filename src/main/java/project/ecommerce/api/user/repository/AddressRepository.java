package project.ecommerce.api.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import project.ecommerce.api.user.entity.Address;
import project.ecommerce.api.user.entity.User;

import java.util.List;

public interface AddressRepository extends JpaRepository<Address, Long> {

    List<Address> findAllByUser(User user);
}
