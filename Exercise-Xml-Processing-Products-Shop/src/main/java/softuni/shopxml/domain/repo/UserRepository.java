package softuni.shopxml.domain.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import softuni.shopxml.domain.entities.User;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {
//SELECT * FROM users
//join products p on users.id = p.buyer_id
    @Query("SELECT DISTINCT u FROM User u " +
            "WHERE u.soldProducts.size > 0 " +
            "ORDER BY u.lastName, u.lastName ")
    List<User> findAllUserWithProductSale();

    @Query("SELECT DISTINCT u FROM User u " +
            "WHERE u.soldProducts.size > 0 " +
            "ORDER BY u.soldProducts.size DESC ")
    List<User> findAllUsersOrderByCountProducts();
}
