package softuni.gameshop.domain.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import softuni.gameshop.domain.entities.Game;

@Repository
public interface GamesRepository extends JpaRepository<Game, Long> {

    Game findGameByTitle(String title);

    Game findGameById(Long id);


}