package softuni.jsonprocessing.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import softuni.jsonprocessing.model.entities.Product;

import java.math.BigDecimal;
import java.util.List;


@Repository
public interface ProductRepository extends JpaRepository<Product,Long> {



    List<Product> findAllByNameContainsAndPriceBetweenOrderByPriceAsc(String input, BigDecimal min,
                                                                      BigDecimal max);

    @Query(value = "select p from Product p where p.buyer.id is not null and p.seller.id = :id " +
            "order by p.buyer.firstName,p.buyer.lastName ")
    List<Product> findAllBySeller(Long id);
}
