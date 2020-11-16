package softuni.springintrousers.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import softuni.springintrousers.domain.entities.Picture;
import softuni.springintrousers.domain.entities.Town;

@Repository
public interface TownRepository extends JpaRepository<Town,Long> {
}
