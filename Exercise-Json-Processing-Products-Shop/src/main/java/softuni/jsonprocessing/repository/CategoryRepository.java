package softuni.jsonprocessing.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import softuni.jsonprocessing.model.entities.Category;

import java.util.List;

public interface CategoryRepository extends JpaRepository<Category,Long> {

    @Query("SELECT c FROM Category c order by c.products.size DESC ")
    List<Category> productByCount ();
}
