package softuni.jsonprocessing.domain.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import softuni.jsonprocessing.domain.entities.Supplier;

import java.util.Set;

@Repository
public interface SupplierRepository extends JpaRepository<Supplier,Long> {

    Set<Supplier> findAllByImporterFalse ();
}
