package alararestaurant.repository;

import alararestaurant.domain.entities.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.Set;

@Repository
public interface ItemRepository extends JpaRepository<Item,Integer> {

    Optional<Item> findFirstByName(String name);

    @Query("SELECT i FROM Item i GROUP BY i.name ")
    Set<Item> findAllByCategoryId (Integer id);
}
