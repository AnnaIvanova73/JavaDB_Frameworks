package softuni.jsonprocessing.domain.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import softuni.jsonprocessing.domain.entities.Sale;

@Repository
public interface SaleRepository extends JpaRepository<Sale,Long> {
}
