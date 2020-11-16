package softuni.springintrousers.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import softuni.springintrousers.domain.entities.Album;

@Repository
public interface AlbumRepository extends JpaRepository<Album,Long> {
}
