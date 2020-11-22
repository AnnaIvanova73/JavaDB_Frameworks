package softuni.jsonprocessing.domain.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import softuni.jsonprocessing.domain.entities.Car;

import java.util.Set;

@Repository
public interface CarRepository extends JpaRepository<Car,Long> {

    Set<Car> findByMakeIsLike(String make);

    @Query("SELECT c FROM Car c WHERE c.make = 'Toyota' ")
    Set<Car> findByMakerToyota();



}
