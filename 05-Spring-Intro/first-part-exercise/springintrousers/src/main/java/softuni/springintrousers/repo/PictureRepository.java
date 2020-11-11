package softuni.springintrousers.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import softuni.springintrousers.domain.entities.Picture;

@Repository
public interface PictureRepository extends JpaRepository<Picture,Long> {
}
