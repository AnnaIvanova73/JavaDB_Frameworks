package softuni.gameshop.domain.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import softuni.gameshop.domain.entities.User;

import java.util.List;

@Repository
public interface UsersRepository extends JpaRepository<User,Long> {


     User findUserByEmailAndPassword (String e, String p);
     User findUserByEmail(String email);
     List<User> findAllByGamesIsNotNull();

}
