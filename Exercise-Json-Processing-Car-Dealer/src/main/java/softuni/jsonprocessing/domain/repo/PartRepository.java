package softuni.jsonprocessing.domain.repo;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import softuni.jsonprocessing.domain.entities.Part;

@Repository
public interface PartRepository extends JpaRepository<Part,Long> {
}
