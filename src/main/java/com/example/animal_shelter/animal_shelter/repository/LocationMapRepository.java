package com.example.animal_shelter.animal_shelter.repository;

import com.example.animal_shelter.animal_shelter.model.LocationMap;
import org.springframework.data.jpa.repository.JpaRepository;

import java.io.IOException;
import java.util.Optional;

public interface LocationMapRepository extends JpaRepository<LocationMap, Long> {
    Optional<LocationMap> findLocationMapById(Long locationMapId);
    Optional<LocationMap> findLocationMapByShelterId(Long shelterId);


}
