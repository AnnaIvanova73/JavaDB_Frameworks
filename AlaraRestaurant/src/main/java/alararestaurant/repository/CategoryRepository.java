package alararestaurant.repository;

import alararestaurant.domain.entities.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.Set;

@Repository
public interface CategoryRepository extends JpaRepository<Category,Integer> {


    Optional<Category> findFirstByNameLike(String name);

    @Query("SELECT c FROM Category c JOIN Item i ON i.category.id = c.id WHERE i.category.id = c.id " +
            "GROUP BY c.name " +
            "ORDER BY c.items.size DESC ,SUM(i.price) DESC ")
    Set<Category> findAllByItemsCount ();
}
