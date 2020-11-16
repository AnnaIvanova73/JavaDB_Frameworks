package softuni.springintro.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import softuni.springintro.domain.entities.Category;

@Repository
public interface CategoryRepository extends JpaRepository<Category,Long> {
}
