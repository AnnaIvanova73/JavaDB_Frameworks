package softuni.labmapping.domain.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import softuni.labmapping.domain.entities.Address;

@Repository
public interface AddressRepository extends JpaRepository<Address,Long> {
}
