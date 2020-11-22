package softuni.shopxml.domain.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import softuni.shopxml.domain.entities.Product;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product,Long> {

    @Query("SELECT p FROM Product p WHERE p.buyer IS NOT NULL AND p.price BETWEEN 500 AND 1000 ORDER BY " +
            "p.price ASC ")
    List<Product> findAllWithPriceInRange();

}
