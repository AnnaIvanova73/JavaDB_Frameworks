package softuni.shopxml.domain.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import softuni.shopxml.domain.entities.Category;

import java.util.List;

@Repository
public interface CategoryRepository extends JpaRepository<Category,Long> {

    @Query("SELECT DISTINCT c FROM Category c ORDER BY c.products.size ")
    List<Category> findAllByProductsSize();
}
