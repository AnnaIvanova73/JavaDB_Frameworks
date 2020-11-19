package softuni.jsonprocessing.domain.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import softuni.jsonprocessing.domain.entities.Car;

import java.util.Set;

@Repository
public interface CarRepository extends JpaRepository<Car,Long> {

    Set<Car> findByMakeIsLike(String make);

}
