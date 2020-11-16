package com.example.lab.domain.repositories;

import com.example.lab.domain.entities.City;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CityRepository extends JpaRepository<City,Long> {



    City findByName(String name);
}
