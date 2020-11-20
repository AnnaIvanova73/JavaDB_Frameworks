package softuni.jsonprocessing.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import softuni.jsonprocessing.model.entities.User;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {


    List<User> findAllByOrderByFirstNameAscLastName();

    @Query("SELECT u FROM User u Where u.buyer is not null order by u.firstName, u.lastName")
    List<User> findInOrder();
}
